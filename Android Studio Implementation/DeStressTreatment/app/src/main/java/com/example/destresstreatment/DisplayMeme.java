package com.example.destresstreatment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.destresstreatment.Model.Meme;
import com.squareup.picasso.Picasso;

public class DisplayMeme extends AppCompatActivity {

    private ImageView displayImage;
    private ImageView likeMeme, unlikeMeme;
    private Button showMore;
    private String cause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_meme);

        cause = (String) getIntent().getSerializableExtra("Cause");
        displayImage = findViewById(R.id.displayImage);
        showMore = findViewById(R.id.showMore);

        Meme meme = (Meme) getIntent().getSerializableExtra("Meme");

        showImage(displayImage, meme.getURL());

        showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMeme.this, ShowMoreMemes.class);
                intent.putExtra("Genre", meme.getSubreddit());
                startActivity(intent);
            }
        });



    }

    private void showImage(ImageView imageView, String link) {

        Picasso.with(DisplayMeme.this).load(link).into(imageView);
    }


}