package com.kbw.cbir;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by hyo99 on 2016-11-10.
 */

public class ResultActivity extends AppCompatActivity {

    LinearLayout imageLayout;
    EditText searchText;

    String textKey;
    int widthLayout;
    int loadImageNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        textKey = pref.getString("content", "No Value");
        widthLayout = pref.getInt("layoutWidth", 0);

        imageLayout = (LinearLayout) findViewById(R.id.imagelayout);
        searchText = (EditText) findViewById(R.id.searchText2);

        searchText.setText(textKey);

        loadingImage();
    }

    public void loadingImage() {

        loadImageNum = 7;

        while(loadImageNum != 0) {
            if(loadImageNum == 1) {
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                ImageView imgView = new ImageView(this);
                imgView.setImageResource(R.drawable.c01);
                imgView.setTag(loadImageNum);
                imgView.setLayoutParams(new LinearLayout.LayoutParams(widthLayout/2, widthLayout/2));
                imgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String str = String.valueOf(view.getTag());
                        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    }
                });

                layout.addView(imgView);
                loadImageNum--;

                imageLayout.addView(layout);
            }
            else {
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                ImageView imgView = new ImageView(this);
                imgView.setImageResource(R.drawable.c01);
                imgView.setTag(loadImageNum);
                imgView.setLayoutParams(new LinearLayout.LayoutParams(widthLayout / 2, widthLayout / 2));
                imgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String str = String.valueOf(view.getTag());
                        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    }
                });
                layout.addView(imgView);
                loadImageNum --;

                ImageView imgView2 = new ImageView(this);
                imgView2.setImageResource(R.drawable.c01);
                imgView2.setTag(loadImageNum);
                imgView2.setLayoutParams(new LinearLayout.LayoutParams(widthLayout / 2, widthLayout / 2));
                imgView2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String str = String.valueOf(view.getTag());
                        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    }
                });
                layout.addView(imgView2);
                loadImageNum --;

                imageLayout.addView(layout);
            }

        }

    }
}
