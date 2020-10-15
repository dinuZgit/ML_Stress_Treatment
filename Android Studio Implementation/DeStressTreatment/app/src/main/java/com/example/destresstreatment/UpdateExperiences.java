package com.example.destresstreatment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateExperiences extends AppCompatActivity {

    private EditText etRecentExp;
    private Button btnUpdateUserExp;
    private String user_id;
    private static final String TAG = "TextClassificationDemo2";
    //private StressClassificationModel model;
    private UserExpDao dao;

    private TextView resultTextView;
    private EditText inputEditText;
    private Handler handler;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_experiences);

        user_id = (String) getIntent().getSerializableExtra("User ID");
        etRecentExp = findViewById(R.id.etRecentExp);
        btnUpdateUserExp = findViewById(R.id.btnUpdateUserExp);
        dao = new UserExpDao(UpdateExperiences.this);

        btnUpdateUserExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String experiences = dao.getExperienceData(user_id);
                long retID = dao.insertData(user_id, etRecentExp.getText().toString());
                if (retID != -1) {
                    Toast.makeText(UpdateExperiences.this, "Insertion successful", Toast.LENGTH_LONG).show();
                    etRecentExp.getText().clear();
                } else {
                    Toast.makeText(UpdateExperiences.this, "Insertion Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}