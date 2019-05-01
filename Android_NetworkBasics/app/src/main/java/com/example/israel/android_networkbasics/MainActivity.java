package com.example.israel.android_networkbasics;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private ProgressBar requestingProgressBar;
    private ImageView comicImageView;

    private XkcdComic currentComic;
    private RequestComicAsyncTask requestComicAsyncTask;
    private CheckBox favoriteCheckbox;

    enum ERequestType {
        Recent,
        Previous,
        Next,
        Random
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestingProgressBar = findViewById(R.id.progress_bar_requesting);
        comicImageView = findViewById(R.id.image_view_comic);
        favoriteCheckbox = findViewById(R.id.checkbox_favorite);

        XkcdDbHelper.initializeInstance(this);

        findViewById(R.id.button_previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestComic(ERequestType.Previous);
            }
        });

        findViewById(R.id.button_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestComic(ERequestType.Next);
            }
        });

        findViewById(R.id.button_random).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestComic(ERequestType.Random);
            }
        });

        // get the recent on init
        requestComic(ERequestType.Recent);

        favoriteCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentComic.getXkcdDbInfo().setFavorite(isChecked ? 1 : 0);
                XkcdDbHelper.updateComic(currentComic);
            }
        });
    }

    private void updateUIPreRequest() {
        requestingProgressBar.setVisibility(View.VISIBLE);
        comicImageView.setVisibility(View.INVISIBLE);

        favoriteCheckbox.setEnabled(false);
    }

    private void updateUIPostRequest(@Nullable XkcdComic xkcdComic) {
        if (xkcdComic == null) {
            return;
        }

        currentComic = xkcdComic;

        if (xkcdComic.getNum() == XkcdDao.MIN_COMIC_NUM) {
            findViewById(R.id.button_previous).setEnabled(false);
        } else {
            findViewById(R.id.button_previous).setEnabled(true);
        }

        if (xkcdComic.getNum() == XkcdDao.getMaxComicNum()) {
            findViewById(R.id.button_next).setEnabled(false);
        } else {
            findViewById(R.id.button_next).setEnabled(true);
        }

        comicImageView.setImageBitmap(xkcdComic.getBitmap());

        favoriteCheckbox.setChecked(xkcdComic.getXkcdDbInfo().getFavorite() == 1);
        favoriteCheckbox.setEnabled(true);
    }

    private void requestComic(ERequestType requestType) {
        if (requestComicAsyncTask != null) {
            return;
        }

        updateUIPreRequest();

        switch (requestType) {
            case Recent: {
                requestComicAsyncTask = new RequestComicAsyncTask();
                requestComicAsyncTask.execute(ERequestType.Recent);
            } break;

            case Previous: {
                requestComicAsyncTask = new RequestComicAsyncTask();
                requestComicAsyncTask.execute(ERequestType.Previous);
            } break;

            case Next: {
                requestComicAsyncTask = new RequestComicAsyncTask();
                requestComicAsyncTask.execute(ERequestType.Next);
            } break;

            case Random: {
                requestComicAsyncTask = new RequestComicAsyncTask();
                requestComicAsyncTask.execute(ERequestType.Random);
            } break;
        }
    }

    private class RequestComicAsyncTask extends AsyncTask<ERequestType, Void, XkcdComic> {

        @Override
        protected XkcdComic doInBackground(ERequestType... requestTypes) {

            // simulates slow loading
/*            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            switch (requestTypes[0]) {
                case Recent: return XkcdDao.getRecentComic();
                case Previous: return XkcdDao.getPreviousComic(currentComic);
                case Next: return XkcdDao.getNextComic(currentComic);
                case Random: return XkcdDao.getRandomComic();
            }

            return null;
        }

        @Override
        protected void onPostExecute(XkcdComic xkcdComic) {
            super.onPostExecute(xkcdComic);

            requestingProgressBar.setVisibility(View.INVISIBLE);
            comicImageView.setVisibility(View.VISIBLE);

            if (isCancelled()) {
                return;
            }

            requestComicAsyncTask = null;

            updateUIPostRequest(xkcdComic);
        }
    }


}
