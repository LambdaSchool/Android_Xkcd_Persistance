package com.example.patrickjmartin.xkcd_persistance;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

        comTitle = findViewById(R.id.comic_title);
        comAlt = findViewById(R.id.comic_alt);
        comImage = findViewById(R.id.comic_image);
        navigation = findViewById(R.id.navigation);

        (new Thread(new Runnable() {
            @Override
            public void run() {
                recentXkcdComic = XkcdDao.getRecentComic();
                newestCom = XkcdDao.getRecentComic().getNum();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI(recentXkcdComic);
                        currentComic = recentXkcdComic;
                    }
                });
            }
        })).start();

        findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new Thread(new Runnable() {
                    @Override
                    public void run() {
                        prevComic = XkcdDao.getPreviousComic(currentComic);
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

        findViewById(R.id.button_random).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new Thread(new Runnable() {
                    @Override
                    public void run() {
                        recentXkcdComic = XkcdDao.getRandomComic();
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

        findViewById(R.id.button_forward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                (new Thread(new Runnable() {
                    @Override
                    public void run() {
                        nextComic = XkcdDao.getNextComic(currentComic);
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




    }

    private void updateUI(XkcdComic c) {
        comTitle.setText(c.getTitle() + " - " + c.getNum());
        comAlt.setText(c.getAlt());
        comImage.setImageBitmap(c.getBitMap());

        if(c.getNum() == newestCom) {
            findViewById(R.id.button_forward).setEnabled(false);
        } else {
            findViewById(R.id.button_forward).setEnabled(true);
        }

        if(c.getNum() == 1) {
            findViewById(R.id.button_back).setEnabled(false);
        } else {
            findViewById(R.id.button_back).setEnabled(true);
        }

    }

}
