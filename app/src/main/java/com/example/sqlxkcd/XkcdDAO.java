package com.example.sqlxkcd;
import org.json.JSONException;
import org.json.JSONObject;

public class XkcdDAO {
        public static final String BASE_URL = "https://xkcd.com/";
        public static final String URL_END = "info.0.json";
        public static final String RECENT_COMIC = BASE_URL + URL_END;
        public static final String SPECIFIC_COMIC = BASE_URL + "%d/" + URL_END;

        public static void getSpecificComic(int num){
            String result = NetworkAdapter.httpRequest(String.format(SPECIFIC_COMIC, num));
            int id = 0;
            String timeStamp = "";

            JSONObject comicObject;
            try{
               comicObject = new JSONObject(result);
            }catch(JSONException e){
                e.printStackTrace();
                comicObject = null;
            }
            try {
                id = comicObject.getInt("num");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                timeStamp = comicObject.getString("year") + comicObject.getString("month");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            
            SqlXkcdDao.addComic(new XkcdComic(id, timeStamp));
        }
}
