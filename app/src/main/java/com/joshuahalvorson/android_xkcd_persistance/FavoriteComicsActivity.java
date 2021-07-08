package com.joshuahalvorson.android_xkcd_persistance;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FavoriteComicsActivity extends AppCompatActivity {
    private TextView favoritesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_comics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        favoritesList = findViewById(R.id.favorites_list);

        ArrayList<String> favorites = XkcdDao.readFavorites();
        for(int i = 0; i < favorites.size(); i++){
            favoritesList.setText(favoritesList.getText().toString() + "\n" + favorites.get(i));
        }

    }
}
