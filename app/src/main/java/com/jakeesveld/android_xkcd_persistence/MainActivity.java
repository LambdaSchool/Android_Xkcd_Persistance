package com.jakeesveld.android_xkcd_persistence;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textViewTitle;
    TextView textViewDetails;
    ImageView imageViewComic;
    static int currentComicId;
    BottomNavigationView navigation;
    CheckBox checkBoxFavorite;
    static Comic currentComic;
    Button buttonFavorite;
    Context context;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_previous:
                    if(currentComicId != 1) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final Comic result = ComicDAO.getPrevious(currentComicId);


                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        currentComicId = result.getId();
                                        currentComic = result;
                                        updateUI();
                                    }
                                });

                            }
                        }).start();
                    }

                    return true;
                case R.id.navigation_random:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final Comic result = ComicDAO.getRandom();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    currentComicId = result.getId();
                                    currentComic = result;
                                    updateUI();
                                }
                            });

                        }
                    }).start();

                    return true;
                case R.id.navigation_next:

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if(currentComicId != ComicDAO.getLatest().getId()) {
                                    final Comic result = ComicDAO.getNext(currentComicId);

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            currentComicId = result.getId();
                                            currentComic = result;
                                            updateUI();
                                        }
                                    });
                                }

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
        XkcdDbDAO.initializeInstance(this);
        checkBoxFavorite = findViewById(R.id.checkbox_favorite);
        textViewDetails = findViewById(R.id.text_view_details);
        textViewTitle = findViewById(R.id.text_view_title);
        imageViewComic = findViewById(R.id.image_view_comic);
        navigation = findViewById(R.id.navigation);
        buttonFavorite = findViewById(R.id.button_favorites);
        currentComicId = 100;
        context = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Comic result = ComicDAO.getLatest();


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        currentComicId = result.getId();
                        currentComic = result;
                        updateUI();

                    }
                });

            }
        }).start();

        checkBoxFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = checkBoxFavorite.isChecked();
                if(!checked == currentComic.getInfo().isFavorite()){
                    currentComic.getInfo().setFavorite(checked);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            XkcdDbDAO.updateComic(currentComic.getInfo());
                        }
                    }).start();
                }
            }
        });

        buttonFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, FavoritesActivity.class));
            }
        });

 /*       checkBoxFavorite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentComic.getInfo().setFavorite(isChecked);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        XkcdDbDAO.updateComic(currentComic.getInfo());
                    }
                }).start();
            }
        });*/


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void updateUI(){
        currentComic.setInfo(XkcdDbDAO.readComic(currentComic.getId()));
        textViewDetails.setText(String.format("Comic Number: %s  %s", currentComic.getId(), currentComic.getDate()));
        textViewTitle.setText(currentComic.getTitle());
        imageViewComic.setImageBitmap(currentComic.getImage());
        imageViewComic.setAdjustViewBounds(true);
        if(currentComic.getInfo() != null) {
            checkBoxFavorite.setChecked(currentComic.getInfo().isFavorite());
        }else{
            currentComic.setInfo(new XkcdDbInfo(currentComic.getId()));
            XkcdDbDAO.addComic(currentComic.getId());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        if(intent != null){
            currentComic = (Comic) intent.getSerializableExtra("Comic");
            currentComicId = currentComic.getId();
            updateUI();
        }
    }
}
