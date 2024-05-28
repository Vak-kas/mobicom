package com.example.week12_hw2;

public class Song {
    private String title;
    private int imageResId;
    private int songResId;
    private String singer;

    public Song(String title, int imageResId, int songResId, String singer) {
        this.title = title;
        this.imageResId = imageResId;
        this.songResId = songResId;
        this.singer = singer;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getSongResId() {
        return songResId;
    }

    public String getSinger() {
        return singer;
    }
}
