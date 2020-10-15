package com.example.destresstreatment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnUpdateExp;
    private Button btnWhyAmIStressed;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnUpdateExp = findViewById(R.id.btnUpdateExp);
        btnWhyAmIStressed = findViewById(R.id.btnWhyAmIStressed);
        user_id = "UID000114";

        btnUpdateExp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UpdateExperiences.class);
                intent.putExtra("User ID", user_id);
                startActivity(intent);
            }
        });

        btnWhyAmIStressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StressCauseIdentification.class);
                intent.putExtra("User ID", user_id);
                startActivity(intent);
            }
        });
    }
}