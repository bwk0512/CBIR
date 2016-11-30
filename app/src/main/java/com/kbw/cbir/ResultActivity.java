package com.kbw.cbir;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by hyo99 on 2016-11-10.
 */

public class ResultActivity extends AppCompatActivity {

    LinearLayout imageLayout;


    int width;

    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        str = pref.getString("content", "No Value");
        width = pref.getInt("layoutWidth", 0);

        imageLayout = (LinearLayout) findViewById(R.id.imagelayout);

        loadingImage();
    }

    public void loadingImage() {

        int num = 7;

        while(num != 0) {
            if(num == 1) {
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                ImageView imgView = new ImageView(this);
                imgView.setImageResource(R.drawable.c01);
                imgView.setLayoutParams(new LinearLayout.LayoutParams(width/2, width/2));

                layout.addView(imgView);

                imageLayout.addView(layout);
                num--;
            }
            else {
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                ImageView imgView = new ImageView(this);
                imgView.setImageResource(R.drawable.c01);
                imgView.setLayoutParams(new LinearLayout.LayoutParams(width / 2, width / 2));
                layout.addView(imgView);

                ImageView imgView2 = new ImageView(this);
                imgView2.setImageResource(R.drawable.c01);
                imgView2.setLayoutParams(new LinearLayout.LayoutParams(width / 2, width / 2));
                layout.addView(imgView2);

                imageLayout.addView(layout);
                num -= 2;
            }

        }

    }
}
