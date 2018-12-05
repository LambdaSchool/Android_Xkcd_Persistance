package com.joshuahalvorson.android_xkcd_persistance;

public class XkcdDbInfo {
    private long timestamp;
    private int favorite;

    public XkcdDbInfo(long timestamp, int favorite) {
        this.timestamp = timestamp;
        this.favorite = favorite;
    }

    public long getTimestamp() {
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
