package com.kbw.cbir;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hyo99 on 2016-11-10.
 */

public class ResultActivity extends AppCompatActivity {

    private DBManager mDBManager;
    private List<Item> mlist;

    LinearLayout imgListlayout; // 이미지리스트 레이아웃
    EditText searchBox; // 검색창
    Button searchButton; // 검색버튼

    String keyword = " "; // 검색어
    int widthLayout; // 이미지 가로길이
    int imgTotalNum; // 불러올 이미지 총 수
    int imgArr[]; // 불러올 이미지 번호

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        imgListlayout = (LinearLayout) findViewById(R.id.imageListlayout);
        searchBox = (EditText) findViewById(R.id.searchBox2);
        searchButton = (Button) findViewById(R.id.searchButton2);

        mDBManager = new DBManager(this);

        // SharedPreferences - 검색어, 레이아웃 가로길이 불러오기
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        keyword = pref.getString("keyword", " ");
        widthLayout = pref.getInt("layoutWidth", 0);

        searchBox.setText(keyword); // 검색창에 keyword 출력유지

        imgArr = new int[100];

        loadDataFromDB(); // 모든 이미지 데이터를 불러옴

        Log.v("keyword is ", keyword);
        loadKeywordImageList(); // 이미지리스트 호출

        // 검색버튼
        searchButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = searchBox.getText().toString(); // 검색어 불러오기

                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("keyword", str);
                editor.commit();

                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                finish();
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

    public void loadKeywordImageList() {

        int i;
        int num = 0;
        // keyword가 포함된 이미지를 찾는다
        for(i=0; i<mlist.size(); i++)
        {
            if(mlist.get(i).getmName().contains(keyword))
            {
                Log.v("일치하는 옷은 ", mlist.get(i).getmName());
                imgArr[num] = mlist.get(i).getmId();
                num++;
            }
        }
        imgTotalNum = num+1;

        i=0;
        while(i < imgTotalNum) {

            if(imgTotalNum-i == 1) {
                // 새로운 레이아웃 생성
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                layout.addView(loadImage(imgArr[i])); // 좌측 이미지
                i++;

                imgListlayout.addView(layout); // 레이아웃에 이미지 넣기
            }
            else if(imgTotalNum-i == 2) {
                // 새로운 레이아웃 생성
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                layout.addView(loadImage(imgArr[i])); // 좌측 이미지
                i++;

                layout.addView(loadImage(imgArr[i])); // 좌측 이미지
                i++;

                imgListlayout.addView(layout); // 레이아웃에 이미지 넣기
            }
            else {
                // 새로운 레이아웃 생성
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                layout.addView(loadImage(imgArr[i])); // 좌측 이미지
                i ++;

                layout.addView(loadImage(imgArr[i])); // 우측 이미지
                i ++;

                layout.addView(loadImage(imgArr[i])); // 우측 이미지
                i ++;

                imgListlayout.addView(layout); // 레이아웃에 이미지 넣기
            }

        }
    }

    public void loadSimilarityImageList() {

        int i;
        int num = 0;
        // keyword가 포함된 이미지를 찾는다
        for(i=0; i<mlist.size(); i++)
        {
            if(mlist.get(i).getmName().contains(keyword))
            {
                imgArr[num] = mlist.get(i).getmId();
                num++;
            }
        }
        imgTotalNum = num+1;

        i=0;
        while(i < imgTotalNum) {

            if(imgTotalNum-i == 1) {
                // 새로운 레이아웃 생성
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                layout.addView(loadImage(imgArr[i])); // 좌측 이미지
                i++;

                imgListlayout.addView(layout); // 레이아웃에 이미지 넣기
            }
            else if(imgTotalNum-i == 2) {
                // 새로운 레이아웃 생성
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                layout.addView(loadImage(imgArr[i])); // 좌측 이미지
                i++;

                layout.addView(loadImage(imgArr[i])); // 좌측 이미지
                i++;

                imgListlayout.addView(layout); // 레이아웃에 이미지 넣기
            }
            else {
                // 새로운 레이아웃 생성
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                layout.addView(loadImage(imgArr[i])); // 좌측 이미지
                i ++;

                layout.addView(loadImage(imgArr[i])); // 우측 이미지
                i ++;

                layout.addView(loadImage(imgArr[i])); // 우측 이미지
                i ++;

                imgListlayout.addView(layout); // 레이아웃에 이미지 넣기
            }

        }
    }


    // 이미지를 불러와 imageView를 생성한다
    public ImageView loadImage(int imageNum) {
        ImageView imgView = new ImageView(this); // 새로운 이미지뷰 생성
        
        imgView.setImageResource(R.drawable.s001 +imageNum-1); // 이미지 불러오기

        imgView.setTag(imageNum); // 이미지 고유태그 달기
        imgView.setLayoutParams(new LinearLayout.LayoutParams(widthLayout / 3, widthLayout / 3)); // 이미지 리사이즈

        // 이미지 클릭하면 유사도 높은 순서대로 출력한다
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = String.valueOf(view.getTag()); // 고유태그 불러오기
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show(); // 체크
            }
        });

        return imgView;
    }

    public void loadDataFromDB() {
        mlist = null;
        mlist = new ArrayList<>();
        mlist = mDBManager.getItem();
    }
}
