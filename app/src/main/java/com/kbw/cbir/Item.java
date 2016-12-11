package com.kbw.cbir;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hyo99 on 2016-12-11.
 */

public class Item {

    @SerializedName("ID")
    private int mId;  //  연도
    @SerializedName("NAME")
    private String mName; // 달
    @SerializedName("URL")
    private String mURL;   // 일\

    /**
     * 생성자
     * @param mId 연도
     * @param mName 달
     * @param mURL 일
     */
    public Item(int mId, String mName, String mURL) {
        this.mId = mId;
        this.mName = mName;
        this.mURL = mURL;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmURL() {
        return mURL;
    }

    public void setmURL(String mURL) {
        this.mURL = mURL;
    }

    @Override
    public String toString() {
        return "Item {" +
                "mId='" + mId + '\'' +
                ", mName=" + mName +
                ", mURL=" + mURL +
                '}';
    }
}
