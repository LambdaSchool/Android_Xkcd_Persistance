package com.example.jacob.android_networkbasics;

class XkcdDbInfo {
    private int timestamp, favorite;

    XkcdDbInfo(int timestamp, int favorite) {
        this.timestamp = timestamp;
        this.favorite = favorite;
    }

    int getTimestamp() {
        return timestamp;
    }

    void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    int getFavorite() {
        return favorite;
    }

    void setFavorite(int favorite) {
        this.favorite = favorite;
    }
}
