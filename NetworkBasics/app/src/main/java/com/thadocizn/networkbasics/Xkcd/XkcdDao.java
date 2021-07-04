package com.thadocizn.networkbasics.Xkcd;

import android.content.Context;
import android.graphics.Bitmap;

import com.thadocizn.networkbasics.classes.NetworkAdapter;
import com.thadocizn.networkbasics.Xkcd.XkcdComic;
import com.thadocizn.networkbasics.data.ComicDbDao;
import com.thadocizn.networkbasics.data.XkcdDbInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class XkcdDao {
    private final static String BASE_URL = "https://xkcd.com/";
    private final static String URL_ENDING = "info.0.json";
    private final static String RECENT_COMIC = BASE_URL + URL_ENDING;
    private final static String SPECIFIC_COMIC = BASE_URL + "%d/" + URL_ENDING;
    public static int maxComicNumber;

    private static XkcdComic getComic(String url, Context context){
        XkcdComic comic = null;
        try {
            ComicDbDao.initializeInstance(context);
            JSONObject jsonObject = new JSONObject(NetworkAdapter.httpRequest(url));
            comic = new XkcdComic(jsonObject);
            Bitmap bitmap = NetworkAdapter.httpImageRequest(comic.getImg());
            comic.setImage(bitmap);
            XkcdDbInfo info = ComicDbDao.readComic(comic.getNum());
            if (info == null){
                info = new XkcdDbInfo(0, (int) System.currentTimeMillis());
                comic.setXkcdDbInfo(info);
                ComicDbDao.createComic(comic);
            }else {

                info.setTimestamp((int) System.currentTimeMillis());
                comic.setXkcdDbInfo(info);
                ComicDbDao.updateComic(comic);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comic;
    }

    public static XkcdComic getRecentComic(Context context){
        XkcdComic comic = getComic(RECENT_COMIC, context);
        maxComicNumber = comic.getNum();
        return comic;
    }

    public static XkcdComic getNextComic(XkcdComic comic, Context context){
        int next = comic.getNum() + 1;
        String url = SPECIFIC_COMIC.replace("%d", Integer.toString(next));
        return getComic(url, context);
    }

    public static XkcdComic getPreviousComic(XkcdComic comic, Context context){
        int previous = comic.getNum() - 1;
        String url = SPECIFIC_COMIC.replace("%d", Integer.toString(previous));
        return getComic(url, context);
    }

    public static XkcdComic getRandomComic(Context context){
        int random = ((int)(Math.random() * maxComicNumber) + 1);
        String url = SPECIFIC_COMIC.replace("%d", Integer.toString(random));
        return getComic(url, context);
    }

    public static void setFavorite(XkcdComic comicTracker) {
        ComicDbDao.updateComic(comicTracker);
    }
}
