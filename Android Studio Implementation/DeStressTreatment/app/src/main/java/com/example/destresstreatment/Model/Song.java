package com.example.destresstreatment.Model;

import java.io.Serializable;

public class Song implements Serializable {

    private String song_id;
    private String title;
    private String link;
    private String artist_name;
    private int listen_count;

    public Song(String song_id, String title, String link, String artist_name, String listen_count) {
        this.song_id = song_id;
        this.title = title;
        this.link = link;
        this.artist_name = artist_name;
        this.listen_count = Integer.parseInt(listen_count);
    }

    public Song() {
    }

    public String getSong_id() {
        return song_id;
    }

    public void setSong_id(String song_id) {
        this.song_id = song_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public int getListen_count() {
        return listen_count;
    }

    public void setListen_count(String listen_count) {
        this.listen_count = Integer.parseInt(listen_count);
    }
}
