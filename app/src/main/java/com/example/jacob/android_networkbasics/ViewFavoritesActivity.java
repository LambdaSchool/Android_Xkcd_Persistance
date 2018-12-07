package com.example.jacob.android_networkbasics;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewFavoritesActivity extends AppCompatActivity {

    Context context;
    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_favorites);

        context = this;
        layout = findViewById(R.id.parent);

        ArrayList<Integer> favorites = new ArrayList<>();
        favorites = XkcdDbDao.readFavorites();
        for (Integer id : favorites) {
            new loadFavorites().execute(id);
        }

    }


    public class loadFavorites extends AsyncTask<Integer, Integer, View> {
        @Override
        protected void onPostExecute(View view) {
            super.onPostExecute(view);
            layout.addView(view);
        }

        @Override
        protected View doInBackground(Integer... integers) {
            final XkcdComic comic = XkcdDao.getComic(integers[0]);
            TextView view = new TextView(context);
            view.setText(comic.getTitle());
            view.setTextSize(28);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra(MainActivity.COMIC_KEY, comic.getNum());
                    startActivity(intent);
                }
            });
            return view;
        }
    }
}
