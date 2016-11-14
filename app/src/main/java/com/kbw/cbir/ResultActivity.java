package com.kbw.cbir;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by hyo99 on 2016-11-10.
 */

public class ResultActivity extends AppCompatActivity {

    TextView textView;

    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textView = (TextView) findViewById(R.id.textView);

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        str = pref.getString("content", "No Value");

        textView.setText(str);

    }
}
