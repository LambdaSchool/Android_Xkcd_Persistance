package com.example.earthdefensesystem.android_networkbasics;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

public class XkcdDao {
    private final static String BASE_URL = "https://xkcd.com/";
    private final static String URL_ENDING = "info.0.json";
    private final static String RECENT_URL = BASE_URL + URL_ENDING;
    private final static String SPECIFIC_URL = BASE_URL + "%d/" + URL_ENDING;
    public static int maxComicNumber;

    private static Comic getComic(String url){
        Comic comic = null;
        try {
            JSONObject jsonObject = new JSONObject(NetworkAdapter.httpRequest(url));
            comic = new Comic(jsonObject);
            Bitmap bitmap = NetworkAdapter.httpImageRequest(comic.getImg());
            comic.setComic(bitmap);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comic;
    }

    public static Comic getRecentComic(){
        Comic comic = getComic(RECENT_URL);
        maxComicNumber = comic.getNum();
        return comic;
    }

    public static Comic getNextComic(Comic comic){
        int next = comic.getNum() + 1;
        String url = SPECIFIC_URL.replace("%d", Integer.toString(next));
        return getComic(url);
    }

    public static Comic getPreviousComic(Comic comic){
        int previous = comic.getNum() - 1;
        String url = SPECIFIC_URL.replace("%d", Integer.toString(previous));
        return getComic(url);
    }

    public static Comic getRandomComic(){
        int random = ((int)(Math.random() * maxComicNumber) + 1);
        String url = SPECIFIC_URL.replace("%d", Integer.toString(random));
        return getComic(url);
    }

}
