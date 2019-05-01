package com.jakeesveld.android_xkcd_persistence;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
        favoritesList = new ArrayList<>();
        listAdapter = new FavoritesListAdapter(favoritesList);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ArrayList<XkcdDbInfo> favoritesInfo = XkcdDbDAO.readAllFavorites();
        for(final XkcdDbInfo info: favoritesInfo){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Comic favoriteComic = ComicDAO.getComic(info.getComicId());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            favoritesList.add(favoriteComic);
                            listAdapter.notifyItemChanged(favoritesList.size() - 1);
                        }
                    });
                }
            }).start();
        }

    }
}
