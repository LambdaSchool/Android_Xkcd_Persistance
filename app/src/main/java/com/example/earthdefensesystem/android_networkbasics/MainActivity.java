package com.example.earthdefensesystem.android_networkbasics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String HTTP_REQUEST = "https://xkcd.com/info.0.json";
    private Button previousButton;
    private Button nextButton;
    private Button randomButton;
    private TextView comicTitle;
    private ImageView comicImage;
    private Comic comic, previous, next, ran;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        previousButton = findViewById(R.id.previous_button);
        nextButton = findViewById(R.id.next_button);
        randomButton = findViewById(R.id.random_button);
        comicTitle = findViewById(R.id.title_text);
        comicImage = findViewById(R.id.comic_image);

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        previous = XkcdDao.getPreviousComic(comic);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateUI(previous);
                                comic = previous;
                            }
                        });

                    }
                }).start();
            }
        });

        findViewById(R.id.random_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ran = XkcdDao.getRandomComic();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateUI(ran);
                                comic = ran;
                            }
                        });
                    }
                }).start();
            }
        });

        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        next =   XkcdDao.getNextComic(comic);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                comic = next;
                                updateUI(next);

                            }
                        });
                    }
                }).start();
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                NetworkAdapter.httpRequest(HTTP_REQUEST);
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
    public void updateUI(Comic comic){
        comicTitle.setText(comic.getTitle());
//        comicTranscript.setText(comic.getTranscript());
        comicImage.setImageBitmap(comic.getComic());
        if (comic.getNum() == XkcdDao.maxComicNumber){
            nextButton.setVisibility(Button.INVISIBLE);
        }else {
            nextButton.setVisibility(Button.VISIBLE);
        }
        if (comic.getNum() == 1){
            previousButton.setVisibility(Button.INVISIBLE);
        }else {
            previousButton.setVisibility(Button.VISIBLE);
        }
    }
}
