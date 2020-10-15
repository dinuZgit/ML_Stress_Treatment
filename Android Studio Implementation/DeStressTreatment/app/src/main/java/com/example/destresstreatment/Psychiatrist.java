package com.example.destresstreatment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.destresstreatment.Model.Meme;
import com.example.destresstreatment.Model.PsychiatristModel;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CALL_PHONE;

public class Psychiatrist extends AppCompatActivity {

    private ListView listViewPsychiatrist;
    private String user_id;
    private String possible_cause;
    private List<PsychiatristModel> list;
    private String psych1, psych2, psych3, psych4;
    private String contact1, contact2, contact3, contact4;
    private Button btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psychiatrist);

        user_id = (String) getIntent().getSerializableExtra("User ID");
        possible_cause = (String) getIntent().getSerializableExtra("Cause");
        listViewPsychiatrist = findViewById(R.id.listViewPsychiatrist);
        btnBackHome = findViewById(R.id.btnBackHome);

        System.out.println("User ID and cause is " + user_id + " " + possible_cause);
        psych1 = "Nivendra Uduman";
        psych2 = "Neil Fernando";
        psych3 = "Shehan Williams";
        psych4 = "Varuni De Silva";
        contact1 = "94112789384";
        contact2 = "0712345678";
        contact3 = "0712345678";
        contact4 = "0712345678";
        PsychiatristModel model1 = new PsychiatristModel(psych1, contact1, "hhhh");
        PsychiatristModel model2 = new PsychiatristModel(psych2, contact2, "mmmm");
        PsychiatristModel model3 = new PsychiatristModel(psych3, contact3, "hhhhg");
        PsychiatristModel model4 = new PsychiatristModel(psych4, contact4, "ttttt");
        System.out.println(model1.getPsychiatrist()+ " " + model1.getContactNumber() + " are the model 1 objects");

        list = new ArrayList<>();
        list.add(model1);
        list.add(model2);
        list.add(model3);
        list.add(model4);

        final PsychAdapter adapter = new PsychAdapter(Psychiatrist.this, list);
        listViewPsychiatrist.setAdapter(adapter);

        listViewPsychiatrist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PsychiatristModel psychiatristModel = list.get(i);

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + psychiatristModel.getContactNumber()));
                //startActivity(callIntent);
                if (ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    startActivity(callIntent);
                } else {
                    requestPermissions(new String[]{CALL_PHONE}, 1);
                }
            }
        });

        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Psychiatrist.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}