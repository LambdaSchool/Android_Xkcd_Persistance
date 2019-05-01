package com.lambdaschool.android_xkcd_persistence;

public class XkcdDbInfo {
    private long timestamp;
    private boolean favorite;

    public XkcdDbInfo() {
        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
