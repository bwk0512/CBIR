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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    DBManager mDBManager;

    private Retrofit mRetrofit;
    private ApiInterface mApiInterface;

    EditText searchBox; // 검색창
    Button searchButton; // 검색버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBox = (EditText) findViewById(R.id.searchBox);
        searchButton = (Button) findViewById(R.id.searchButton);

        mDBManager = new DBManager(this);
        mDBManager.dropAllTable();
        mDBManager.CreateAllTable();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        mRetrofit = new Retrofit.Builder().baseUrl(mApiInterface.API_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        mApiInterface = mRetrofit.create(ApiInterface.class);

        loadDataFromServer();

        // 검색버튼
        searchButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = searchBox.getText().toString(); // 검색어 불러오기
                Log.v("Main keyword is ", str);

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

    // 서버의 모든 데이터를 불러와서 List에 저장하는 함수
    public void loadDataFromServer() {

        Log.d("Run : ", "Good job");

        Call<List<Item>> selectAllItem = mApiInterface.selectAllItem();
        selectAllItem.enqueue(new retrofit2.Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                Log.d("Run2 : ", "Good job2");

                List<Item> list = response.body();
                if(list!=null) {
                    for(Item r : list) {
                        Log.i("tag", r.toString());
                        mDBManager.insertItem(r);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                Log.d("Run2 : ", "Fail");
            }
        });
    }

    // 서버와의 통신을 위한 interface
    public interface ApiInterface {
        public static final String API_URL = "http://bwk.iptime.org:12777";

        @GET("/test.php")
        Call<List<Item>> selectAllItem ();
    }
}
