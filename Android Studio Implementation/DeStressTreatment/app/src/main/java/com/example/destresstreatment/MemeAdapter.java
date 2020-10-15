package com.example.destresstreatment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.destresstreatment.Model.Meme;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MemeAdapter extends ArrayAdapter<Meme> {

    private Activity context;
    private List<Meme> memes;

    public MemeAdapter(Activity context, List<Meme> memes) {
        super(context, R.layout.activity_meme_adapter, memes);
        this.context = context;
        this.memes = memes;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();

        final View listView = layoutInflater.inflate(R.layout.activity_meme_adapter, null, true);
        ImageView imageViewMeme = (ImageView) listView.findViewById(R.id.imageViewMeme);

        final Meme memeList = memes.get(position);
        Picasso.with(getContext()).load(memeList.getURL()).into(imageViewMeme);

        return imageViewMeme;
    }
}
