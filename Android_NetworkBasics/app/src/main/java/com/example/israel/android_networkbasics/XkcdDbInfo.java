package com.example.israel.android_networkbasics;

public class XkcdDbInfo {

    public XkcdDbInfo(long timestamp, int favorite) {
        this.timestamp = timestamp;
        this.favorite = favorite;
    }

    public XkcdDbInfo() {
        timestamp = System.currentTimeMillis();
        favorite = 0;
    }

    private long timestamp;
    private int favorite;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
