package com.example.destresstreatment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.FirebaseDatabase;

public class RecommendationsInterface extends AppCompatActivity {

    private Button btnMusic;
    private Button btnMemes;
    private Button btnUpdatePreferences;
    private String user_id;
    private String cause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations_interface);

        /*FirebaseDatabase.getInstance().getReference().child("memes").child("Android").setValue("abcd");
        FirebaseDatabase.getInstance().getReference().child("meme_types").child("Android").setValue("abcd");
        FirebaseDatabase.getInstance().getReference().child("artist_names").child("Android").setValue("abcd");
        FirebaseDatabase.getInstance().getReference().child("songs").child("Android").setValue("abcd");
        FirebaseDatabase.getInstance().getReference().child("user_preferences").child("Android").setValue("abcd");*/
        user_id = (String) getIntent().getSerializableExtra("User ID");
        cause = (String) getIntent().getSerializableExtra("Cause");

        btnMusic = findViewById(R.id.btnMusic);
        btnMemes = findViewById(R.id.btnMemes);
        btnUpdatePreferences = findViewById(R.id.btnUpdatePreferences);

        btnMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecommendationsInterface.this, MusicRecommendations.class);
                intent.putExtra("User ID", user_id);
                intent.putExtra("Cause", cause);
                startActivity(intent);
            }
        });

        btnMemes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecommendationsInterface.this, MemeRecommendations.class);
                intent.putExtra("Current User ID", user_id);
                intent.putExtra("Possible Cause", cause);
                startActivity(intent);
            }
        });

        btnUpdatePreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecommendationsInterface.this, UpdatePreferences.class);
                intent.putExtra("Current User ID", user_id);
                intent.putExtra("Possible Cause", cause);
                startActivity(intent);
            }
        });
    }
}