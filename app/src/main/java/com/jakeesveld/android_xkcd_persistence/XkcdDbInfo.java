package com.jakeesveld.android_xkcd_persistence;

public class XkcdDbInfo {

    private boolean favorite;
    private long timestamp;
    private int comicId;

    public XkcdDbInfo(int favorite, long timestamp, int comicId) {
        this.favorite = favorite == 1;
        this.timestamp = timestamp;
        this.comicId = comicId;
    }

    public XkcdDbInfo(boolean favorite, long timestamp, int comicId) {
        this.favorite = favorite;
        this.timestamp = timestamp;
        this.comicId = comicId;
    }

    public XkcdDbInfo() {
        this.favorite = false;
        this.timestamp = System.currentTimeMillis();
    }

    public XkcdDbInfo(int comicId){
        this.comicId = comicId;
        this.favorite = false;
        this.timestamp = System.currentTimeMillis();
    }

    public int getFavorite() {
        if(favorite){
            return 1;
        }else{
            return 0;
        }
    }

    public boolean isFavorite() {
        return favorite;
    }

    public int getComicId() {
        return comicId;
    }

    public void setComicId(int comicId) {
        this.comicId = comicId;
    }

    public void setFavorite(int favoriteInt) {
        this.favorite = favoriteInt == 1;
    }

    public void setFavorite(boolean favorite){
        this.favorite = favorite;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
