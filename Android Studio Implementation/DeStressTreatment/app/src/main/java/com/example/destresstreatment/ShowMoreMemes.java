package com.example.destresstreatment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.destresstreatment.Model.Meme;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShowMoreMemes extends AppCompatActivity {

    private ListView listViewMoreMemes;
    private String genre;
    private List<Meme> meme;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more_memes);

        listViewMoreMemes = findViewById(R.id.listViewMoreMemes);
        meme = new ArrayList<>();
        genre = (String) getIntent().getSerializableExtra("Genre");

        databaseReference = FirebaseDatabase.getInstance().getReference("memes");
        Query query1 = databaseReference.orderByChild("Subreddit").equalTo(genre);

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                meme.clear();

                for (DataSnapshot me: snapshot.getChildren()) {
                    Meme meme1 = me.getValue(Meme.class);
                    meme.add(meme1);
                }

                Random random = new Random();
                for (int i = 0; i < 10; i++){
                    int randomeIndex = random.nextInt(meme.size());

                    meme.add(meme.get(randomeIndex));
                }

                final MemeAdapter adapter = new MemeAdapter(ShowMoreMemes.this, meme);
                listViewMoreMemes.setAdapter(adapter);

                Toast.makeText(ShowMoreMemes.this, "Loading memes for you. Please wait", Toast.LENGTH_LONG);

                listViewMoreMemes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Meme meme3 = meme.get(i);

                        Intent intent = new Intent(ShowMoreMemes.this, DisplayMeme.class);
                        intent.putExtra("Meme", meme3);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}