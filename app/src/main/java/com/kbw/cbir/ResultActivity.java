package com.kbw.cbir;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by hyo99 on 2016-11-10.
 */

public class ResultActivity extends AppCompatActivity {

    LinearLayout imageLayout;

    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        str = pref.getString("content", "No Value");

        imageLayout = (LinearLayout) findViewById(R.id.imagelayout);

        loadingImage();
    }

    public void loadingImage() {

        int num = 2;

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        while(num != 0) {
            ImageView imgView = new ImageView(this);
            imgView.setImageResource(R.drawable.c01);

            //imgView.setLayoutParams();

            layout.addView(imgView);

            num--;
        }

        imageLayout.addView(layout);
    }
}
