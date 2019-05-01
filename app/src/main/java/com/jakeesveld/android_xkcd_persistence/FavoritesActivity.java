package com.jakeesveld.android_xkcd_persistence;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    Context context;
    RecyclerView recyclerView;
    FavoritesListAdapter listAdapter;
    ArrayList<Comic> favoritesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);


    }
}
