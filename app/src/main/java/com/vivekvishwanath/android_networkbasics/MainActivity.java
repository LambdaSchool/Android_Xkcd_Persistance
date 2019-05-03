package com.vivekvishwanath.android_networkbasics;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements FavoritesFragment.OnListFragmentInteractionListener {

    private ImageView comicImage;
    private TextView comicText;
    private BottomNavigationItemView nextButton;
    private BottomNavigationItemView previousButton;
    private XkcdComic comic;
    private CheckBox favoriteCheckBox;
    Button favoritesButton;
    Context context;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.previous_button:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            comic = XkcdDao.getPreviousComic(comic);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateUI(comic);
                                }
                            });
                        }
                    }).start();
                    return true;
                case R.id.random_button:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            comic = XkcdDao.getRandomComic();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateUI(comic);
                                }
                            });
                        }
                    }).start();
                    return true;
                case R.id.next_button:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            comic = XkcdDao.getNextComic(comic);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    updateUI(comic);
                                }
                            });
                        }
                    }).start();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        XkcdDbDao.initializeInstance(context);

        comicImage = findViewById(R.id.comic_image);
        comicText = findViewById(R.id.comic_text);
        nextButton = findViewById(R.id.next_button);
        previousButton = findViewById(R.id.previous_button);
        favoriteCheckBox = findViewById(R.id.favorite_check_box);
        favoritesButton = findViewById(R.id.favorites_button);
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoritesFragment fragment = new FavoritesFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.list_fragment_container, fragment)
                        .commit();
            }
        });

        favoriteCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                XkcdDao.setFavorite(comic, isChecked);
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        new Thread(new Runnable() {
            @Override
            public void run() {
                comic = XkcdDao.getRecentComic();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(comic);
                    }
                });
            }
        }).start();
    }

    private void updateUI (XkcdComic comic) {
        if (comic.getNum() == 1) {
            previousButton.setVisibility(View.GONE);
        } else if(comic.getNum() == XkcdDao.maxComicNumber) {
            nextButton.setVisibility(View.GONE);
        } else {
            nextButton.setVisibility(View.VISIBLE);
            previousButton.setVisibility(View.VISIBLE);
        }
        comicImage.setImageBitmap(comic.getImage());
        comicText.setText(comic.getAlt());
        favoriteCheckBox.setChecked(comic.getDbInfo().isFavorite());
    }

    @Override
    public void onListFragmentInteraction(XkcdComic comic) {

    }
}
