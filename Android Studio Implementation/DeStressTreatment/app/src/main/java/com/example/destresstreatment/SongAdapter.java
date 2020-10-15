package com.example.destresstreatment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.destresstreatment.Model.Song;

import java.util.List;

public class SongAdapter extends ArrayAdapter<Song> {

    private Activity context;
    private List<Song> songs;
    String databaseReference;

    public SongAdapter(Activity context, List<Song> songs) {
        super(context, R.layout.activity_song_adapter, songs);
        this.context = context;
        this.songs = songs;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        final View listView = inflater.inflate(R.layout.activity_song_adapter, null, true);
        TextView tvTitle = (TextView) listView.findViewById(R.id.tvTitle);
        TextView tvListenCount = (TextView) listView.findViewById(R.id.tvListenCount);

        final Song list = songs.get(position);
        tvTitle.setText(list.getTitle() + " - " + list.getArtist_name());
        //tvListenCount.setText(String.valueOf(list.getListen_count()));

        return listView;
    }
}