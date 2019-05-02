package com.lambdaschool.android_xkcd_persistence;

public class XkcdDbInfo {
    private long timestamp;
    private int favorite;

    public XkcdDbInfo() {
        this.timestamp = System.currentTimeMillis();
        this.favorite = 0;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int isFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
