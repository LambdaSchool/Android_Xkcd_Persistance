package com.lambdaschool.android_xkcd_persistence;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

public class UserSelectionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selections);
        final Context context = this;

        Switch switch1 = findViewById(R.id.switch1);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    buttonView.setText("History");
                    LinearLayout linearLayout = findViewById(R.id.linear_layout);
                    linearLayout.removeAllViews();
                } else {
                    buttonView.setText("Favorites");

                    showFavorites(context);
                }
            }
        });

        showFavorites(context);
    }

    private void showFavorites(Context context) {
        final LinearLayout linearLayout = findViewById(R.id.linear_layout);
        linearLayout.removeAllViews();
        ArrayList<XkcdDbInfo> xkcdDbInfoArrayList = XkcdDao.getFavorites();
        for (XkcdDbInfo xkcdDbInfo : xkcdDbInfoArrayList) {
            TextView textView = new TextView(context);
            textView.setText(String.format(Locale.US, "ID %d - @ %d", xkcdDbInfo.getFavoriteId(), xkcdDbInfo.getTimestamp()));
            textView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    XkcdDbInfo xkcdDbInfo = new XkcdDbInfo();

                    String[] componentText = ((TextView) v).getText().toString().split("-");
                    xkcdDbInfo.setFavoriteId(Integer.parseInt(componentText[0].trim().substring(3)));
                    xkcdDbInfo.setTimestamp(Long.parseLong(componentText[1].trim().substring(2)));
                    xkcdDbInfo.setFavorite(0);

                    XkcdDao.setFavorite(new XkcdComic(xkcdDbInfo));
                    linearLayout.removeView(v);

                    return true;
                }
            });

            linearLayout.addView(textView);
        }
    }
}
