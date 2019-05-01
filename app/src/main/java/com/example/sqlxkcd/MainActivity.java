package com.example.sqlxkcd;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            public void run() {
                String result = NetworkAdapter.httpRequest(String.format(XkcdDAO.SPECIFIC_COMIC, 443));
                Log.i("test2", result);
            }
        }).start();
    }
}
