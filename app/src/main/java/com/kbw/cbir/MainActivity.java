package com.kbw.cbir;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText searchBox; // 검색창
    Button searchButton; // 검색버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBox = (EditText) findViewById(R.id.searchBox);
        searchButton = (Button) findViewById(R.id.searchButton);

        // 검색버튼
        searchButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = searchBox.getText().toString(); // 검색어 불러오기

                // SharedPreferences - 검색어 저장
                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("keyword", str);
                editor.commit();

                // 화면 전환
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                startActivity(intent);

            }
        });

        // 검색창 : 엔터가 입력되면 '검색버튼' 호출
        searchBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == keyEvent.KEYCODE_ENTER)
                {
                    searchButton.performClick();
                    return true;
                }
                return false;
            }
        });
    }

    // 레이아웃의 길이를 구하기 위한 함수
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.activity_main);

        // SharedPreferences - 레이아웃 가로길이 저장
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("layoutWidth", mainLayout.getWidth());
        editor.commit();
    }
}
