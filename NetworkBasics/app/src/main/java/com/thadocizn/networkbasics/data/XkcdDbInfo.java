package com.thadocizn.networkbasics.data;

public class XkcdDbInfo {
    private int timestamp;
    private int bool;

    public XkcdDbInfo(int boolLastRead, int timestamp) {
        this.bool = boolLastRead;
        this.timestamp = timestamp;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getBool() {
        return bool;
    }

    public void setBool(int bool) {
        this.bool = bool;
    }
}
