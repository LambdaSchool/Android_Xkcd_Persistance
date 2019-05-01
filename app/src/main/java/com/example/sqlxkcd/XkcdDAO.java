package com.example.sqlxkcd;
import org.json.JSONException;
import org.json.JSONObject;

public class XkcdDAO {
        public static final String BASE_URL = "https://xkcd.com/";
        public static final String URL_END = "info.0.json";
        public static final String RECENT_COMIC = BASE_URL + URL_END;
        public static final String SPECIFIC_COMIC = BASE_URL + "%d/" + URL_END;

        public static XkcdComic getComic(String urlString){
            String result = NetworkAdapter.httpRequest(BASE_URL);

            JSONObject comicObject;
            try{
               comicObject = new JSONObject(result);
            }catch(JSONException e){
                e.printStackTrace();
                comicObject = null;
            }
            XkcdComic comic = new XkcdComic(comicObject);
            return comic;
        }
}
