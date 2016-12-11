package com.kbw.cbir;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    LinearLayout imgListlayout; // 이미지리스트 레이아웃
    EditText searchBox; // 검색창
    Button searchButton; // 검색버튼

    String keyword; // 검색어
    int widthLayout; // 이미지 가로길이
    int imgTotalNum; // 불러올 이미지 총 수

    private Retrofit mRetrofit;
    private ApiInterface mApiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        imgListlayout = (LinearLayout) findViewById(R.id.imageListlayout);
        searchBox = (EditText) findViewById(R.id.searchBox2);
        searchButton = (Button) findViewById(R.id.searchButton2);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        mRetrofit = new Retrofit.Builder().baseUrl(mApiInterface.API_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        mApiInterface = mRetrofit.create(ApiInterface.class);

        // SharedPreferences - 검색어, 레이아웃 가로길이 불러오기
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        keyword = pref.getString("keyword", "No Value");
        widthLayout = pref.getInt("layoutWidth", 0);

        searchBox.setText(keyword); // 검색창에 keyword 출력유지

        //selectData();

        loadImageList(); // 이미지리스트 호출
    }

    public void loadImageList() {

        imgTotalNum = 7;

        while(imgTotalNum != 0) {

            // imgTotalNum가 홀수일 때문 if문 동작
            if(imgTotalNum == 1) {
                // 새로운 레이아웃 생성
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                layout.addView(loadImage()); // 좌측 이미지
                imgTotalNum--;

                imgListlayout.addView(layout); // 레이아웃에 이미지 넣기
            }
            else {
                // 새로운 레이아웃 생성
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.HORIZONTAL);

                layout.addView(loadImage()); // 좌측 이미지
                imgTotalNum --;

                layout.addView(loadImage()); // 우측 이미지
                imgTotalNum --;

                imgListlayout.addView(layout); // 레이아웃에 이미지 넣기
            }

        }
    }

    public ImageView loadImage() {
        ImageView imgView = new ImageView(this); // 새로운 이미지뷰 생성
        imgView.setImageResource(R.drawable.c01); // 이미지 불러오기
        imgView.setTag(imgTotalNum); // 이미지 고유태그 달기
        imgView.setLayoutParams(new LinearLayout.LayoutParams(widthLayout / 2, widthLayout / 2)); // 이미지 리사이즈
        // 이미지 클릭시 동작
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = String.valueOf(view.getTag()); // 고유태그 불러오기
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show(); // 체크
                selectData();
            }
        });

        return imgView;
    }

    public void selectData() {

        Log.d("Run : ", "Good job");

        Call<List<Item>> selectAllItem = mApiInterface.selectAllItem();
        selectAllItem.enqueue(new retrofit2.Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                Log.d("Run2 : ", "Good job2");

                List<Item> list = response.body();

                for(int i=0; i<list.size(); i++) {
                    Log.d("name : ", list.get(i).getmName());
                    Log.d("url : ", list.get(i).getmURL());
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.d("Run2 : ", "Fail");
            }
        });
    }

    public interface ApiInterface {
        public static final String API_URL = "http://bwk.iptime.org:12777";

        @GET("/test.php")
        Call<List<Item>> selectAllItem ();
    }

}
