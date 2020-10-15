package com.example.destresstreatment.Model;

import java.io.Serializable;

public class Meme implements Serializable {

    private String Title;
    private String Subreddit;
    private String URL;

    public Meme(String title, String subreddit, String URL) {
        Title = title;
        Subreddit = subreddit;
        this.URL = URL;
    }

    public Meme() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getSubreddit() {
        return Subreddit;
    }

    public void setSubreddit(String subreddit) {
        Subreddit = subreddit;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }
}
