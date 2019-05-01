package com.jakeesveld.android_xkcd_persistence;

import android.graphics.Bitmap;
import android.media.Image;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Comic {
    private String title;
    private Bitmap image;
    private int id;
    private String date;
    private XkcdDbInfo info;

    public Comic(JSONObject json){
        try {
            this.title = json.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.image = NetworkAdapter.httpImageRequest(json.getString("img"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            this.id = json.getInt("num");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            this.date = String.format("%s/%s/%s", json.getString("month"), json.getString("day"), json.getString("year"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.info = new XkcdDbInfo(this.id);
    }

    public XkcdDbInfo getInfo() {
        return info;
    }

    public void setInfo(XkcdDbInfo info) {
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }
}
