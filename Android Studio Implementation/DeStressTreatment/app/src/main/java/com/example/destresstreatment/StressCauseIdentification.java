package com.example.destresstreatment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;

public class StressCauseIdentification extends AppCompatActivity {

    private TextView tvresultCauseText;
    private Button btnRecommendations;
    private StressClassificationModel model;
    private static final String TAG = "TextClassificationDemo2";
    private UserExpDao dao;
    private Handler handler;
    private String user_id;
    private String cause;
    private String experiences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress_cause_identification);

        user_id = (String) getIntent().getSerializableExtra("User ID");
        dao = new UserExpDao(StressCauseIdentification.this);
        handler = new Handler();
        model = new StressClassificationModel(getApplicationContext());

        tvresultCauseText = findViewById(R.id.tvresultCauseText);
        btnRecommendations = findViewById(R.id.btnRecommendations);

    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.v(TAG, "OnStart");
        handler.post(
                () -> {
                    model.load();
                    experiences = dao.getExperienceData(user_id);
                    System.out.println("User experiences inside cause identification class is " + experiences);
                    classify(experiences);
                    System.out.println("Cause is " + cause);
                    btnRecommendations.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            /*Intent intent = new Intent(StressCauseIdentification.this, RecommendationsInterface.class);
                            intent.putExtra("User ID", user_id);
                            intent.putExtra("Cause", cause);
                            startActivity(intent);*/
                            if (cause.equals("Suicide")) {
                                Intent intent = new Intent(StressCauseIdentification.this, Psychiatrist.class);
                                intent.putExtra("User ID", user_id);
                                intent.putExtra("Cause", cause);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(StressCauseIdentification.this, RecommendationsInterface.class);
                                intent.putExtra("User ID", user_id);
                                intent.putExtra("Cause", cause);
                                startActivity(intent);
                            }
                        }
                    });
                }
        );
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "OnStop");
        handler.post(
                () -> {
                    model.load();
                }
        );
    }

    private void classify(final String text) {

        System.out.println("Text inside classify of Main Activity " + text);
        handler.post(
                () -> {
                    //List<TextClassificationClient.Result> results = null;
                    String results = "";
                    try {
                        results = model.classify(text);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cause = results;
                    showResult(results);
                }
        );
    }

    /*private void showResult(final String inputText, final List<TextClassificationClient.Result> results){
        runOnUiThread(
                () -> {
                    String textToShow = "Input : " + inputText + "\nOutput : \n";
                    for (int i = 0; i < results.size(); i++) {
                        TextClassificationClient.Result result = results.get(i);
                        textToShow += String.format("    %s: %s\\n", result.getTitle(), result.getConfidence());
                    }

                    textToShow += "---------\\n";

                    resultTextView.append(textToShow);
                    inputEditText.getText().clear();

                    scrollView.post(() -> scrollView.fullScroll(View.FOCUS_DOWN));
                }
        );
    }*/

    private void showResult(final String result) {

        tvresultCauseText.setText(result);

    }
}