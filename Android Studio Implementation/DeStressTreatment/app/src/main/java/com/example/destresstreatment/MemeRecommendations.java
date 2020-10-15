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
import com.example.destresstreatment.Model.Song;
import com.example.destresstreatment.Model.UserPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemeRecommendations extends AppCompatActivity implements Serializable {

    private ListView listViewMemes;
    private List<Meme> memes1, memes2, memes3, finalMemes;
    private String genre1, genre2, genre3;
    private DatabaseReference databaseReference;
    private String user_id, possible_cause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme_recommendations);

        user_id = (String) getIntent().getSerializableExtra("Current User ID");
        possible_cause = (String) getIntent().getSerializableExtra("Possible Cause");
        listViewMemes = findViewById(R.id.listViewMemes);

        finalMemes = new ArrayList<>();
        findGenrePreferences();
    }

    private void findGenrePreferences() {

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("user_preferences");
        Query query1 = databaseReference1.orderByChild("user_id").equalTo(user_id);

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user: snapshot.getChildren()){
                    UserPreference userPreference = user.getValue(UserPreference.class);
                    String cause = userPreference.getPossible_cause();
                    System.out.println(cause + " is the fucking cause");
                    if (cause.equals(possible_cause)){
                        genre1 = userPreference.getGenreOne();
                        genre2 = userPreference.getGenreTwo();
                        genre3 = userPreference.getGenreThree();
                    }
                }

                getMemesFromGenre(genre1, genre2, genre3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getMemesFromGenre(String genre1, String genre2, String genre3) {

        memes1 = new ArrayList<>();
        memes2 = new ArrayList<>();
        memes3 = new ArrayList<>();
        List<Meme> memeList1 = new ArrayList<>();
        List<Meme> memeList2 = new ArrayList<>();
        List<Meme> memeList3 = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("memes");
        Query query1 = databaseReference.orderByChild("Subreddit").equalTo(genre1);

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                memes1.clear();

                for (DataSnapshot datas: snapshot.getChildren()) {
                    Meme meme = datas.getValue(Meme.class);
                    //System.out.println(meme.getScore());
                    memes1.add(meme);
                }

                //memes1.sort(Comparator.comparing(Meme::getScore).reversed());

                /*for (int i = 0; i < 5; i++){
                    memeList1.add(memes1.get(i));
                }*/


                Random random = new Random();
                for (int i = 0; i < 10; i++){
                    int randomeIndex = random.nextInt(memes1.size());

                    memeList1.add(memes1.get(randomeIndex));
                }

                Query query2 = databaseReference.orderByChild("Subreddit").equalTo(genre2);

                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        memes2.clear();

                        for (DataSnapshot datas: snapshot.getChildren()){
                            Meme meme = datas.getValue(Meme.class);
                            //System.out.println(meme.getScore());
                            memes2.add(meme);
                        }

                        //memes2.sort(Comparator.comparing(Meme::getScore).reversed());

                        /*for (int j = 0; j < 5; j++) {
                            memeList2.add(memes2.get(j));
                        }*/

                        Random random = new Random();
                        for (int i = 0; i < 10; i++){
                            int randomeIndex = random.nextInt(memes2.size());

                            memeList2.add(memes2.get(randomeIndex));
                        }

                        Query query3 = databaseReference.orderByChild("Subreddit").equalTo(genre3);

                        query3.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                memes3.clear();

                                for (DataSnapshot datas: snapshot.getChildren()){
                                    Meme meme = datas.getValue(Meme.class);
                                    memes3.add(meme);
                                }

                                //memes3.sort(Comparator.comparing(Meme::getScore).reversed());

                                /*for (int l = 0; l < 5; l++){
                                    memeList3.add(memes3.get(l));
                                }*/

                                Random random = new Random();
                                for (int i = 0; i < 10; i++){
                                    int randomeIndex = random.nextInt(memes3.size());

                                    memeList3.add(memes3.get(randomeIndex));
                                }

                                List<Meme> memeslist = new ArrayList<>();
                                memeslist = memeList1;
                                memeslist.addAll(memeList2);
                                finalMemes = memeslist;
                                finalMemes.addAll(memeList3);

                                final MemeAdapter adapter = new MemeAdapter(MemeRecommendations.this, finalMemes);
                                listViewMemes.setAdapter(adapter);

                                Toast.makeText(MemeRecommendations.this, "Loading memes for you. Please wait", Toast.LENGTH_LONG);

                                listViewMemes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        Meme meme = finalMemes.get(i);

                                        Intent intent = new Intent(MemeRecommendations.this, DisplayMeme.class);
                                        intent.putExtra("Meme", meme);
                                        intent.putExtra("Cause", possible_cause);
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}