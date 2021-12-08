package com.example.studentcrimeapp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Crime(){
        mId = UUID.randomUUID();
        mDate = Date.from(Instant.now());
    }

    public Crime(String title, Date date, boolean solved){
        mId = UUID.randomUUID();
        mTitle = title;
        mDate = date;
        mSolved = solved;
    }
    public UUID getmId() {
        return mId;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmTitle() {
        return mTitle;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean ismSolved() {
        return mSolved;
    }

    public void setmSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

}
