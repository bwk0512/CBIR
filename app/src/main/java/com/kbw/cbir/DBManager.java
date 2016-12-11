package com.kbw.cbir;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyo99 on 2016-08-02.
 */


/**
 * Local DataBase
 */
public class DBManager extends SQLiteOpenHelper {

    private String DB_NAME; // DB 파일 이름
    private String RECORD_TABLE; // 테이블 이름
    private String ITEM_TABLE; // 테이블 이름

    /**
     * 생성자
     * @param context MainActivity의 Context
     */
    public DBManager(Context context) {
        super(context, context.getResources().getString(R.string.app_name), null, 1);
        DB_NAME = context.getResources().getString(R.string.app_name);        // app name의 DB table 생성
        RECORD_TABLE = "RECORD";
        ITEM_TABLE = "ITEM";
    }

    /**
     * onCreate
     * @param db SQLite에서 데이터베이스를 쓰기위한 파라미터
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS RECORD_LIST"); // 테이블 삭제 - 초기화
        //onCreate(db); // RECORD_LIST 테이블 다시 생성
    }

    /**
     * Record 테이블을 생성한다
     */
    public void CreateAllTable() {
        SQLiteDatabase db = getWritableDatabase();

        StringBuffer sb = new StringBuffer();

        sb.append(" CREATE TABLE " + ITEM_TABLE + " ( ");
        sb.append(" ID INTEGER PRIMARY KEY, "); // 의미없음
        sb.append(" NAME TEXT, ");
        sb.append(" URL TEXT ) ");

        db.execSQL(sb.toString());

        db.close();

    }

    public void insertItem(Item data) {

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("INSERT INTO " + ITEM_TABLE + " (ID, NAME, URL) VALUES (" +
                data.getmId() + "," +
                "'" + data.getmName() + "'," +
                "'" + data.getmURL() + "')");
        db.close();
    }

    public List<Item> getItem() {

        SQLiteDatabase db = null;
        List<Item> list = null;
        list = new ArrayList<>();
        Item item = null;

        try {
            db = getReadableDatabase();

            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT * FROM " + ITEM_TABLE);
            Cursor cursor = db.rawQuery(sb.toString(), null);

            while (cursor.moveToNext()) {
                item = new Item(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2));
                list.add(item);
            }
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return list;
    }

    /**
     * Record 테이블 삭제
     */
    public void dropAllTable() {

        SQLiteDatabase db2 = getWritableDatabase();
        db2.execSQL("DROP TABLE IF EXISTS " + ITEM_TABLE); // 쿼리문 입력
        db2.close();

    }

    /**
     * Record 테이블의 현재 행의 갯수를 반환한다
     * @return 행의 갯수
     */
    public int getRowCount() {
        int cnt = 0;

        SQLiteDatabase db = getWritableDatabase(); // 데이터베이스 불러오기 - 쓰기전용

        Cursor cursor; // 테이블 한줄한줄 읽어오기 위한 Cursor 클래스
        cursor = db.rawQuery("SELECT * from "+ ITEM_TABLE, null); // RECORD_LIST 테이블 전부 콜
        while(cursor.moveToNext()) { // 테이블이 끝 날때까지 동작하는 반복문
            cnt++;
        }
        cursor.close();
        db.close();

        return cnt;
    }
}
