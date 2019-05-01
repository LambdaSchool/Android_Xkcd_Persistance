package com.example.israel.android_networkbasics;

import android.graphics.Bitmap;
import android.support.annotation.WorkerThread;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class XkcdDao {

    public static final String URL_BASE = "https://xkcd.com/";
    public static final String URL_ENDING = "info.0.json";
    public static final String RECENT_COMIC = URL_BASE + URL_ENDING;
    public static final int UNINITIALIZED = -1;
    public static int MIN_COMIC_NUM = 1;
    private static int maxComicNum = UNINITIALIZED;

    public static boolean isInitialized() {
        return maxComicNum != UNINITIALIZED;
    }

    public static int getMaxComicNum() {
        return maxComicNum;
    }

    @WorkerThread
    private static XkcdComic getComic(String urlStr) {
        String jsonStr = NetworkAdapter.httpRequestGET(urlStr);
        if (jsonStr == null) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            XkcdComic xkcdComic = new XkcdComic(jsonObject);
            Bitmap bitmap = NetworkAdapter.httpImageRequestGET(xkcdComic.getImg());
            xkcdComic.setBitmap(bitmap);

            XkcdDbInfo xkcdDbInfo = XkcdDbHelper.readComic(xkcdComic.getNum());
            if (xkcdDbInfo == null) { // have not seen yet
                xkcdDbInfo = new XkcdDbInfo();
                xkcdComic.setXkcdDbInfo(xkcdDbInfo);
                XkcdDbHelper.createComic(xkcdComic);
            } else {
                xkcdDbInfo.setTimestamp(System.currentTimeMillis());
                xkcdComic.setXkcdDbInfo(xkcdDbInfo);
                XkcdDbHelper.updateComic(xkcdComic);
            }
            return xkcdComic;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @WorkerThread
    public static XkcdComic getRecentComic() {
        XkcdComic recentComic = getComic(RECENT_COMIC);
        if (recentComic != null) {
            maxComicNum = recentComic.getNum();
        }
        return recentComic;
    }

    @WorkerThread
    public static XkcdComic getPreviousComic(XkcdComic xkcdComic) {
        if (!isInitialized()) {
            getRecentComic();
        }

        if (xkcdComic == null) {
            return null;
        }
        return getComic(URL_BASE + (xkcdComic.getNum() - 1) + "/" + URL_ENDING);
    }

    @WorkerThread
    public static XkcdComic getNextComic(XkcdComic xkcdComic) {
        if (!isInitialized()) {
            getRecentComic();
        }

        if (xkcdComic == null) {
            return null;
        }
        return getComic(URL_BASE + (xkcdComic.getNum() + 1) + "/" + URL_ENDING);
    }

    @WorkerThread
    public static XkcdComic getRandomComic() {
        if (!isInitialized()) {
            getRecentComic();
        }

        int randInt = (int)(Math.random() * maxComicNum);
        return getComic(URL_BASE + randInt + "/" + URL_ENDING);
    }

    public static ArrayList<Integer> getFavorites() {
        return XkcdDbHelper.readFavorites();
    }

}
