package com.joshuahalvorson.android_xkcd_persistance;

public class XkcdDbInfo {
    private long timestamp;
    private int favorite, comicId;


    public XkcdDbInfo(long timestamp, int favorite, int comicId) {
        this.timestamp = timestamp;
        this.favorite = favorite;
        this.comicId = comicId;
    }

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
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
