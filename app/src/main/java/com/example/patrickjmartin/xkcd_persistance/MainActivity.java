package com.example.patrickjmartin.xkcd_persistance;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import android.support.design.widget.BottomNavigationView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView comImage;
    private TextView comTitle, comAlt;

    private XkcdComic prevComic;
    private XkcdComic currentComic;
    private XkcdComic nextComic;
    private XkcdComic recentXkcdComic;
    private BottomNavigationView navigation;
    private int newestCom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}
