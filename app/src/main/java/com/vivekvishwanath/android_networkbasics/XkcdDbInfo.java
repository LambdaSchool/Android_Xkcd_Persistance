package com.vivekvishwanath.android_networkbasics;

public class XkcdDbInfo {

    private int timestamp, favorite;

    public XkcdDbInfo(int timestamp, int favorite) {
        this.timestamp = timestamp;
        this.favorite = favorite;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
