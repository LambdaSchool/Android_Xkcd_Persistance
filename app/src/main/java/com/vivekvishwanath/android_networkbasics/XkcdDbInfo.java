package com.vivekvishwanath.android_networkbasics;

public class XkcdDbInfo {

    private long timestamp;
    private boolean favorite;

    public XkcdDbInfo(int timestamp, boolean favorite) {
        this.timestamp = timestamp;
        this.favorite = favorite;
    }

    public XkcdDbInfo() {
        favorite = false;
        timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void updateTimestamp() {
        this.timestamp = System.currentTimeMillis();
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
