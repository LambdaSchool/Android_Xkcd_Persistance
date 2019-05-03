package com.lambdaschool.android_xkcd_persistence;

public class XkcdDbInfo {
    private long timestamp;
    private int favorite;
    private int favoriteId;

    public XkcdDbInfo() {
        this.timestamp = System.currentTimeMillis();
        this.favorite = 0;
        this.favoriteId = -1;
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

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }
}
