package com.example.destresstreatment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.destresstreatment.Model.Song;
import com.example.destresstreatment.Model.UserPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MusicRecommendations extends AppCompatActivity {

    private ListView listView;
    private List<Song> songs1, songs2, songs3, finalList;
    private DatabaseReference databaseReference;
    private String artistName1, artistName2, artistName3;
    private String[] artists;
    public static String link;
    private String user_id, possible_cause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_recommendations);

        user_id = (String) getIntent().getSerializableExtra("User ID");
        possible_cause = (String) getIntent().getSerializableExtra("Cause");
        listView = findViewById(R.id.listView);

        findUserPreferences();
        //artistName1 = "Taylor Swift";
        //artistName2 = "Coldplay";
        //artistName3 = "Maroon 5";
        //System.out.println(artistName1 + " " + artistName2 + " " + artistName3);

        finalList = new ArrayList<>();
        //getSongsFromArtist(artistName1, artistName2, artistName3);

    }

    private void findUserPreferences() {

        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("user_preferences");
        Query query1 = databaseReference1.orderByChild("user_id").equalTo(user_id);

        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot user: snapshot.getChildren()) {
                    UserPreference userPreference = user.getValue(UserPreference.class);
                    String cause = userPreference.getPossible_cause();
                    System.out.println(cause + " is the fucking cause");
                    if (cause.equals(possible_cause)){
                        artistName1 = userPreference.getArtistOne();
                        artistName2 = userPreference.getArtistTwo();
                        artistName3 = userPreference.getArtistThree();
                    }
                }

                getSongsFromArtist(artistName1, artistName2, artistName3);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getSongsFromArtist(String artistName1, String artistName2, String artistName3) {

        songs1 = new ArrayList<>();
        songs2 = new ArrayList<>();
        songs3 = new ArrayList<>();
        List<Song> songList1 = new ArrayList<>();
        List<Song> songsList2 = new ArrayList<>();
        List<Song> songsList3 = new ArrayList<>();

        System.out.println("Just after initializing arraylists");

        databaseReference = FirebaseDatabase.getInstance().getReference("songs");
        Query query1 = databaseReference.orderByChild("artist_name").equalTo(artistName1);
        System.out.println("Query execution");
        System.out.println(artistName1 + " " + artistName2 + " " + artistName3);
        query1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                songs1.clear();

                for (DataSnapshot datas: snapshot.getChildren()) {
                    Song song = datas.getValue(Song.class);
                    songs1.add(song);
                    System.out.println(song.getTitle());
                }

                songs1.sort(Comparator.comparing(Song::getListen_count).reversed());
                songList1.clear();
                for (int i = 0; i < 5; i++) {
                    songList1.add(songs1.get(i));
                }

                Query query2 = databaseReference.orderByChild("artist_name").equalTo(artistName2);
                query2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        songs2.clear();

                        for (DataSnapshot datas: snapshot.getChildren()) {
                            Song song = datas.getValue(Song.class);
                            songs2.add(song);
                        }

                        songs2.sort(Comparator.comparing(Song::getListen_count).reversed());

                        for (int i = 0; i < 5; i++) {
                            songsList2.add(songs2.get(i));
                        }

                        Query query3 = databaseReference.orderByChild("artist_name").equalTo(artistName3);
                        query3.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                songs3.clear();

                                for (DataSnapshot datas: snapshot.getChildren()) {
                                    Song song = datas.getValue(Song.class);
                                    songs3.add(song);
                                }

                                songs3.sort(Comparator.comparing(Song::getListen_count).reversed());

                                for (int i = 0; i < 5; i++) {
                                    songsList3.add(songs3.get(i));
                                }

                                List<Song> list = new ArrayList<>();
                                list = songList1;
                                list.addAll(songsList2);
                                finalList = list;
                                finalList.addAll(songsList3);

                                final SongAdapter adapter = new SongAdapter(MusicRecommendations.this, finalList);
                                listView.setAdapter(adapter);

                                //link = null;

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                        Song song = finalList.get(i);
                                        //link = song.getLink();
                                        System.out.println(song.getTitle());

                                        Intent intent = new Intent(getApplicationContext(), PlayMusic.class);
                                        intent.putExtra("Songs", song);
                                        System.out.println(song.getTitle());
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