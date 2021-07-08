package com.joshuahalvorson.android_xkcd_persistance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView comicTitle, comicAlt;
    private ImageView comicImage;
    private Button favoriteButton, viewFavoritesButton;

    private XkcdComic recentXkcdComic;
    private XkcdComic nextComic;
    private XkcdComic prevComic;
    private XkcdComic currentComic;

    private int newestComic;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        context = this;

        comicTitle = findViewById(R.id.comic_title);
        comicAlt = findViewById(R.id.comic_alt);
        comicImage = findViewById(R.id.comic_image);
        favoriteButton = findViewById(R.id.favorite_button);
        viewFavoritesButton = findViewById(R.id.view_favorites_button);

        viewFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FavoriteComicsActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.previous_comic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new Thread(new Runnable() {
                    @Override
                    public void run() {
                        prevComic = XkcdDao.getPreviousComic(currentComic, context);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateUI(prevComic);
                                currentComic = prevComic;
                            }
                        });
                    }
                })).start();
            }
        });

        findViewById(R.id.random_comic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new Thread(new Runnable() {
                    @Override
                    public void run() {
                        recentXkcdComic = XkcdDao.getRandomComic(context);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateUI(recentXkcdComic);
                                currentComic = recentXkcdComic;
                            }
                        });
                    }
                })).start();
            }
        });

        findViewById(R.id.next_comic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new Thread(new Runnable() {
                    @Override
                    public void run() {
                        nextComic = XkcdDao.getNextComic(currentComic, context);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateUI(nextComic);
                                currentComic = nextComic;
                            }
                        });
                    }
                })).start();
            }
        });

        (new Thread(new Runnable() {
            @Override
            public void run() {
                recentXkcdComic = XkcdDao.getRecentComic(context);
                newestComic = recentXkcdComic.getNum();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(recentXkcdComic);
                        currentComic = recentXkcdComic;
                    }
                });
            }
        })).start();

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XkcdDbInfo xkcdDbInfo = currentComic.getXkcdDbInfo();
                if(xkcdDbInfo.getFavorite() == 1){
                    xkcdDbInfo.setFavorite(0);
                    XkcdDao.updateComic(currentComic);
                    favoriteButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
                }else{
                    xkcdDbInfo.setFavorite(1);
                    XkcdDao.updateComic(currentComic);
                    favoriteButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorYellow));
                }
            }
        });
    }

    private void updateUI(XkcdComic xkcdComic){
        favoriteButton.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimary));
        comicTitle.setText(xkcdComic.getTitle() + " (Comic #" + xkcdComic.getNum() + ")");
        comicAlt.setText(xkcdComic.getAlt());
        comicImage.setImageBitmap(xkcdComic.getBitMap());
        if(xkcdComic.getNum() == newestComic){
            findViewById(R.id.next_comic).setVisibility(View.INVISIBLE);
        }else{
            findViewById(R.id.next_comic).setVisibility(View.VISIBLE);
        }
        if(xkcdComic.getNum() == 1){
            findViewById(R.id.previous_comic).setVisibility(View.INVISIBLE);
        }else{
            findViewById(R.id.previous_comic).setVisibility(View.VISIBLE);
        }
    }

}
