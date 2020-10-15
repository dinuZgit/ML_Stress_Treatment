package com.example.destresstreatment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.annotation.WorkerThread;

import org.json.JSONException;
import org.json.JSONObject;
import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class StressClassificationModel {

    private static final String TAG = "DeStressTreatment1";
    private static final String MODEL_PATH = "stress_classification_model_two.tflite";
    private static final String DIC_PATH = "words_dict_two.txt";
    private static final String LABEL_PATH = "labels.txt";

    private static final int SENTENCE_LEN = 256;
    public static Integer MODEL_INPUT_SHAPE = 34746;
    private static final String SIMPLE_SPACE_OR_PUNCTUATION = " |\\\\,|\\\\.|\\\\!|\\\\?|\\n";

    private static final int MAX_RESULTS = 6;

    private final Context context;
    private final Map<String, Integer> dic = new HashMap<>();
    private final List<String> labels = new ArrayList<>();
    private Interpreter tflite;

    public static class Result {

        private final String id;
        private final String title;
        private final Float confidence;

        public Result(String id, String title, Float confidence) {
            this.id = id;
            this.title = title;
            this.confidence = confidence;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public Float getConfidence() {
            return confidence;
        }

        @SuppressLint("DefaultLocale")
        @Override
        public String toString() {
            String resultString = "";

            if (id != null) {
                resultString += "[" + id + "] ";
            }

            if (title != null) {
                resultString += title + " ";
            }

            if (confidence != null) {
                resultString += String.format("(%.1f%%) ", confidence * 100.0f);
            }

            return resultString.trim();
        }
    };

    public StressClassificationModel(Context context) {
        this.context = context;
    }

    @WorkerThread
    public void load() {
        loadModel();
        //loadDictionary();
        loadLabels();
    }

    @WorkerThread
    private synchronized void loadModel() {
        try {
            ByteBuffer buffer = loadModelFile(this.context.getAssets());
            tflite = new Interpreter(buffer);
            Log.v(TAG, "TFLite Model Loaded");

        } catch (IOException ex) {
            Log.v(TAG, ex.getMessage());
        }
    }

    @WorkerThread
    private synchronized void loadLabels() {
        try {
            loadLabelFile(this.context.getAssets());
            Log.v(TAG, "Labels Loaded");
        } catch (IOException ex) {
            Log.v(TAG, ex.getMessage());
        }
    }

    @WorkerThread
    private synchronized void unload(){
        tflite.close();
        dic.clear();
        labels.clear();
    }

    private static MappedByteBuffer loadModelFile(AssetManager assetManager) throws IOException {

        try(AssetFileDescriptor fileDescriptor = assetManager.openFd(MODEL_PATH);
            FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor())) {
            FileChannel fileChannel = inputStream.getChannel();
            long startOffset = fileDescriptor.getStartOffset();
            long declaredLength = fileDescriptor.getDeclaredLength();
            return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
        }
    }

    private void loadLabelFile(AssetManager assetManager) throws IOException{
        try (InputStream ins = assetManager.open(LABEL_PATH);
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(ins))){
            while (bufferedReader.ready()) {
                labels.add(bufferedReader.readLine());
            }

            System.out.println("Labels are " + labels);
        }
    }

    float[][] tokenizeInputText(String text) throws JSONException {
        String clean_text = text.toLowerCase();
        clean_text = clean_text.replaceAll("[.,:?{}]+", " ");
        clean_text = clean_text.trim();
        System.out.println(clean_text + " is the clean text");
        //float[][] input = new float[1][MODEL_INPUT_SHAPE];

        float[] tmp = new float[130];
        int i = 0;
        JSONObject word_dict = new JSONObject(readJSONFromAsset());

        String[] words = clean_text.split(" ");
        System.out.println(Arrays.toString(words) + " is the words var");
        for (String word : words) {

            if (word_dict.has(word)) {
                int index = word_dict.getInt(word);
                System.out.println(index + " is the index inside dic word finding");
                tmp[i++] = index;
            }
        }

        System.out.println(i + " is i value");
        System.out.println(Arrays.toString(tmp) + " is the input before going to classify func");
        int newSize = 130-i;
        float[] temp = new float[newSize];
        Arrays.fill(temp, 0);
        //Arrays.fill(tmp, i, 130 - 1, 0);
        float[] result = new float[130];
        int pos = 0;

        for (float element: temp){
            result[pos] = 0;
            pos++;
        }

        System.out.println(pos + " is the pos value now");
        for (float element: tmp) {
            System.out.println(element + " is the element");
            result[pos] = element;
            pos++;
            if (pos == 130){
                break;
            }
        }

        float[][] ans = {result};
        System.out.println("result is " + result);

        return ans;
    }

    public String readJSONFromAsset() {
        String json = null;
        try {
            AssetManager assetManager = this.context.getAssets();
            InputStream is = assetManager.open(DIC_PATH);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @WorkerThread
    public synchronized String classify(String text) throws JSONException {
        //text = "If there is any variation or roadblock to the plan, then I get irritated and anxious. Like for instance, if someone schedules a meeting on my usual naptime.";
        float[][] input = new float[1][130];
        input = tokenizeInputText(text);
        Log.v(TAG, "Classifying with TFLite");

        float[][] output = new float[1][labels.size()];
        tflite.run(input, output);

        PriorityQueue<Result> pq = new PriorityQueue<>(
                MAX_RESULTS, (lhs, rhs) -> Float.compare(rhs.getConfidence(), lhs.getConfidence()));
        for(int i = 0; i < labels.size(); i++) {
            pq.add(new Result("" + i, labels.get(i), output[0][i]));
        }

        float max = (float) 0.0;
        String cause = "";
        for(int j = 0; j < labels.size(); j++) {
            if (output[0][j] > max){
                max = output[0][j];
                cause = labels.get(j);
            } else {
                max = max;
                System.out.println("Current max is " + max + " and label is "+ cause);
            }
        }

        /*Iterator iterator = pq.iterator();

        while (iterator.hasNext()){
            System.out.println(iterator.next() + " is the accessed element");
            Sampler.Value values = (Sampler.Value) iterator.next();
            System.out.println("the element value is " + values);
        }*/

        final ArrayList<Result> results = new ArrayList<>();
        while (!pq.isEmpty()){
            results.add(pq.poll());
        }

        return cause;
    }

    Map<String, Integer> getDic() {
        return this.dic;
    }

    Interpreter getTflite() {
        return this.tflite;
    }

    List<String> getLabels(){
        return this.labels;
    }
}
