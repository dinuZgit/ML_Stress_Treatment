package com.example.destresstreatment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.destresstreatment.Model.UserPreference;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class UpdatePreferences extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner causesSpinner;
    private Spinner artistOneSpinner, artistTwoSpinner, artistThreeSpinner;
    private Spinner genreOneSpinner, genreTwoSpinner, genreThreeSpinner;
    private Button btnUpdate;
    private DatabaseReference databaseReference1, databaseReference2;
    private ArrayList<String> arrayList1, arrayList2;
    private String user_id, possible_cause;
    private String artistOne, artistTwo, artistThree;
    private String genreOne, genreTwo, genreThree;
    private UserPreference userPreference;
    String[] causes = {"Possible Cause", "Anxiety", "Break ups", "CPTSD", "Depression", "Divorce"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_preferences);

        user_id = (String) getIntent().getSerializableExtra("Current User ID");
        System.out.println(user_id);

        causesSpinner = findViewById(R.id.causesSpinner);
        artistOneSpinner = findViewById(R.id.artistOneSpinner);
        artistTwoSpinner = findViewById(R.id.artistTwoSpinner);
        artistThreeSpinner = findViewById(R.id.artistThreeSpinner);
        genreOneSpinner = findViewById(R.id.genreOneSpinner);
        genreTwoSpinner = findViewById(R.id.genreTwoSpinner);
        genreThreeSpinner = findViewById(R.id.genreThreeSpinner);
        btnUpdate = findViewById(R.id.btnUpdate);

        causesSpinner.setOnItemSelectedListener(this);
        artistOneSpinner.setOnItemSelectedListener(this);
        artistTwoSpinner.setOnItemSelectedListener(this);
        artistThreeSpinner.setOnItemSelectedListener(this);
        genreOneSpinner.setOnItemSelectedListener(this);
        genreTwoSpinner.setOnItemSelectedListener(this);
        genreThreeSpinner.setOnItemSelectedListener(this);

        userPreference = new UserPreference();
        arrayList1 = new ArrayList<>();
        arrayList2 = new ArrayList<>();

        possible_cause = "Possible Cause";
        artistOne = "Choose Artist";
        artistTwo = "Choose Artist";
        artistThree = "Choose Artist";
        genreOne = "Choose Genre";
        genreTwo = "Choose Genre";
        genreThree = "Choose Genre";

        //FirebaseDatabase.getInstance().getReference().child("artist_names").child("Android").setValue("abcd");
        //FirebaseDatabase.getInstance().getReference().child("meme_types").child("Android").setValue("abcd");

        showCausesDataSpinner();
        showArtistDataSpinner();
        showGenreDataSpinner();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData(user_id, possible_cause, artistOne, artistTwo, artistThree, genreOne, genreTwo, genreThree);
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        possible_cause = String.valueOf(causesSpinner.getSelectedItem());
        artistOne = String.valueOf(artistOneSpinner.getSelectedItem());
        artistTwo = String.valueOf(artistTwoSpinner.getSelectedItem());
        artistThree = String.valueOf(artistThreeSpinner.getSelectedItem());
        genreOne = String.valueOf(genreOneSpinner.getSelectedItem());
        genreTwo = String.valueOf(genreTwoSpinner.getSelectedItem());
        genreThree = String.valueOf(genreThreeSpinner.getSelectedItem());

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



    private void showCausesDataSpinner() {

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UpdatePreferences.this, R.layout.support_simple_spinner_dropdown_item, causes);
        causesSpinner.setAdapter(arrayAdapter);
    }

    private void showArtistDataSpinner() {

        databaseReference1 = FirebaseDatabase.getInstance().getReference("artist_names");
        databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList1.clear();

                for (DataSnapshot item: snapshot.getChildren()) {
                    System.out.println(item.child("artist_names").getValue(String.class));
                    arrayList1.add(item.child("artist_names").getValue(String.class));
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UpdatePreferences.this, R.layout.support_simple_spinner_dropdown_item, arrayList1);
                artistOneSpinner.setAdapter(arrayAdapter);
                artistTwoSpinner.setAdapter(arrayAdapter);
                artistThreeSpinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showGenreDataSpinner() {

        databaseReference2 = FirebaseDatabase.getInstance().getReference("meme_types");
        databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList2.clear();

                for (DataSnapshot item: snapshot.getChildren()) {
                    arrayList2.add(item.child("Subreddit").getValue(String.class));
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(UpdatePreferences.this, R.layout.support_simple_spinner_dropdown_item, arrayList2);
                genreOneSpinner.setAdapter(arrayAdapter);
                genreTwoSpinner.setAdapter(arrayAdapter);
                genreThreeSpinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void saveData(final String user_id, final String possible_cause, String artistOne, String artistTwo, String artistThree, String genreOne, String genreTwo, String genreThree) {

        if (possible_cause.equals("Possible Cause") || artistOne.equals("Choose Artist") || artistTwo.equals("Choose Artist") || artistThree.equals("Choose Artist") || genreOne.equals("Choose Genre") || genreTwo.equals("Choose Genre") || genreThree.equals("Choose Genre")) {
            Toast.makeText(UpdatePreferences.this, "Please don't leave any field blank", Toast.LENGTH_LONG).show();
        } else {

            userPreference.setUser_id(user_id);
            userPreference.setPossible_cause(possible_cause);
            userPreference.setArtistOne(artistOne);
            userPreference.setArtistTwo(artistTwo);
            userPreference.setArtistThree(artistThree);
            userPreference.setGenreOne(genreOne);
            userPreference.setGenreTwo(genreTwo);
            userPreference.setGenreThree(genreThree);

            /*final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user_preferences");

            //Query query = databaseReference.orderByChild("user_id").equalTo(user_id);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot user: snapshot.getChildren()) {
                            String userid = user.child("user_id").getValue(String.class);
                            System.out.println(userid + " found");
                            if(userid.equals(user_id)) {
                                String cause = user.child("possible_cause").getValue(String.class);
                                if (cause.equals(possible_cause)) {
                                    String key = user.child("key_reference").getValue(String.class);
                                    System.out.println("Key of target is " + key);
                                    databaseReference.child(key).setValue(null);
                                }
                            }
                            *//*UserPreference userPref = snapshot.getValue(UserPreference.class);
                            if (userPref.getPossible_cause().equals(possible_cause)) {
                                String key = userPref.getKey_reference();
                                System.out.println(key + " is the key");
                                //Query query1 = databaseReference.orderByChild("key_reference").equalTo(key);
                                databaseReference.child(key).setValue(null);
                            }*//*
                        }
                    }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });*/

            final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("user_preferences");

            String id = databaseRef.push().getKey();
            userPreference.setKey_reference(id);
            databaseRef.child(id).setValue(userPreference);

            Toast.makeText(UpdatePreferences.this, "User preference updated", Toast.LENGTH_LONG).show();

            showCausesDataSpinner();
            showArtistDataSpinner();
            showGenreDataSpinner();
        }
    }
}