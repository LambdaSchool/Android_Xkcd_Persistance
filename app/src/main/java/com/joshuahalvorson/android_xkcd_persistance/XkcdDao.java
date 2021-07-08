package com.joshuahalvorson.android_xkcd_persistance;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class XkcdDao {
    private final static String BASE_URL = "https://xkcd.com/";
    private final static String URL_ENDING = "info.0.json";
    private final static String RECENT_COMIC = BASE_URL + URL_ENDING;
    private final static String SPECIFIC_COMIC = BASE_URL + "%d/" + URL_ENDING;

    public static int maxComicNumber;

    private static XkcdComic getComic(String url, Context context){
        XkcdComic xkcdComic = null;
        try {
            XkcdDbDao.initializeInstance(context);
            JSONObject jsonObject = new JSONObject(NetworkAdapter.httpRequest(url));
            xkcdComic = new XkcdComic(jsonObject);
            Bitmap imageBitMap = NetworkAdapter.httpImageRequest(xkcdComic.getImg());
            xkcdComic.setBitMap(imageBitMap);
            XkcdDbInfo xkcdDbInfo = XkcdDbDao.readComic(xkcdComic.getNum());
            if(xkcdDbInfo == null){
                xkcdDbInfo = new XkcdDbInfo(System.currentTimeMillis() / 1000, 0, xkcdComic.getNum());
                xkcdComic.setXkcdDbInfo(xkcdDbInfo);
                XkcdDbDao.createComic(xkcdComic);
            }else{
                xkcdDbInfo.setTimestamp((int)System.currentTimeMillis()/1000);
                xkcdDbInfo.setComicId(xkcdComic.getNum());
                xkcdComic.setXkcdDbInfo(xkcdDbInfo);
                XkcdDbDao.updateComic(xkcdComic);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return xkcdComic;
    }

    public static XkcdComic getRecentComic(Context context){
        XkcdComic xkcdComic = getComic(RECENT_COMIC, context);
        maxComicNumber = xkcdComic.getNum();
        return xkcdComic;
    }

    public static XkcdComic getNextComic(XkcdComic xkcdComic, Context context){
        int nextComic = xkcdComic.getNum() + 1;
        String newUrl = String.format(SPECIFIC_COMIC, nextComic);
        return getComic(newUrl, context);
    }

    public static XkcdComic getPreviousComic(XkcdComic xkcdComic, Context context){
        int previousComic = xkcdComic.getNum() - 1;
        String newUrl = String.format(SPECIFIC_COMIC, previousComic);
        return getComic(newUrl, context);
    }

    public static XkcdComic getRandomComic(Context context){
        int randomComicNum = ((int)(Math.random() * maxComicNumber) + 1);
        String newUrl = String.format(SPECIFIC_COMIC, randomComicNum);
        return getComic(newUrl, context);
    }

    public static void updateComic(XkcdComic xkcdComic){
        XkcdDbDao.updateComic(xkcdComic);
    }

    public static ArrayList<String> readFavorites(){
        return XkcdDbDao.readFavorites();
    }


}
