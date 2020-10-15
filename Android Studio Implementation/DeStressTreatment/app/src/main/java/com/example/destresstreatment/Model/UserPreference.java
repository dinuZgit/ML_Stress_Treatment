package com.example.destresstreatment.Model;

public class UserPreference {

    private String user_id;
    private String possible_cause;
    private String artistOne;
    private String artistTwo;
    private String artistThree;
    private String genreOne;
    private String genreTwo;
    private String genreThree;
    private String key_reference;

    public UserPreference(String user_id, String possible_cause, String artistOne, String artistTwo, String artistThree, String genreOne, String genreTwo, String genreThree, String key_reference) {
        this.user_id = user_id;
        this.possible_cause = possible_cause;
        this.artistOne = artistOne;
        this.artistTwo = artistTwo;
        this.artistThree = artistThree;
        this.genreOne = genreOne;
        this.genreTwo = genreTwo;
        this.genreThree = genreThree;
        this.key_reference = key_reference;
    }

    public UserPreference() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPossible_cause() {
        return possible_cause;
    }

    public void setPossible_cause(String possible_cause) {
        this.possible_cause = possible_cause;
    }

    public String getArtistOne() {
        return artistOne;
    }

    public void setArtistOne(String artistOne) {
        this.artistOne = artistOne;
    }

    public String getArtistTwo() {
        return artistTwo;
    }

    public void setArtistTwo(String artistTwo) {
        this.artistTwo = artistTwo;
    }

    public String getArtistThree() {
        return artistThree;
    }

    public void setArtistThree(String artistThree) {
        this.artistThree = artistThree;
    }

    public String getGenreOne() {
        return genreOne;
    }

    public void setGenreOne(String genreOne) {
        this.genreOne = genreOne;
    }

    public String getGenreTwo() {
        return genreTwo;
    }

    public void setGenreTwo(String genreTwo) {
        this.genreTwo = genreTwo;
    }

    public String getGenreThree() {
        return genreThree;
    }

    public void setGenreThree(String genreThree) {
        this.genreThree = genreThree;
    }

    public String getKey_reference() {
        return key_reference;
    }

    public void setKey_reference(String key_reference) {
        this.key_reference = key_reference;
    }
}
