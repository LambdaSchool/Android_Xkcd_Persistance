package com.vivekvishwanath.android_networkbasics;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class XkcdDao {

    private static final String BASE_URL = "https://xkcd.com/";
    private static final String URL_ENDING = "info.0.json";
    private static final String RECENT_COMIC_URL = BASE_URL + URL_ENDING;
    private static final String SPECIFIC_COMIC_URL = BASE_URL + "%d/" + URL_ENDING;
    public static int maxComicNumber;

    private static XkcdComic getComic(String urlString) {
        XkcdComic comic = null;
        if (NetworkAdapter.httpRequest(urlString) != null) {
            try {
                JSONObject xkcdJSON = new JSONObject(NetworkAdapter.httpRequest(urlString));
                comic = new XkcdComic(xkcdJSON);
                comic.setImage(NetworkAdapter.httpImageRequest(comic.getImg()));
                XkcdDbInfo dbInfo = XkcdDbDao.readComic(comic.getNum());
                if (dbInfo == null) {
                    dbInfo = new XkcdDbInfo();
                    comic.setDbInfo(dbInfo);
                    XkcdDbDao.createComic(comic);
                } else {
                    dbInfo.updateTimestamp();
                    comic.setDbInfo(dbInfo);
                    XkcdDbDao.updateComic(comic);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return comic;
    }

    public static XkcdComic getSpecificComic(int id) {
        XkcdComic comic = getComic(String.format(SPECIFIC_COMIC_URL, id));
        return comic;
    }

    public static XkcdComic getRecentComic() {
        XkcdComic comic = getComic(RECENT_COMIC_URL);
        maxComicNumber = comic.getNum();
        return comic;
    }

    public static XkcdComic getNextComic(XkcdComic comic) {
        int currentNum = comic.getNum();
        currentNum++;
        String newComicUrl = String.format(SPECIFIC_COMIC_URL, currentNum);
        return getComic(newComicUrl);
    }

    public static XkcdComic getPreviousComic(XkcdComic comic) {
        int currentNum = comic.getNum();
        currentNum--;
        String newComicUrl = String.format(SPECIFIC_COMIC_URL, currentNum);
        return getComic(newComicUrl);
    }

    public static XkcdComic getRandomComic() {
        int randomNum = (int) (Math.random() * maxComicNumber + 1);
        String randomUrl = String.format(SPECIFIC_COMIC_URL, randomNum);
        return getComic(randomUrl);
    }

    public static void setFavorite(XkcdComic comic, boolean favorite) {
        XkcdDbInfo dbInfo = comic.getDbInfo();
        if (favorite) {
            dbInfo.setFavorite(true);
        } else {
            comic.getDbInfo().setFavorite(false);
        }
        XkcdDbDao.updateComic(comic);
    }

    public static ArrayList<Integer> getFavorites() {
        return XkcdDbDao.readFavorites();
    }

}
