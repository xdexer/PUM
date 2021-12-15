package com.example.studentcrimeapp;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity
public class Crime implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "uuid")
    private UUID UU;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "date")
    private Date mDate;

    @ColumnInfo(name = "solved")
    private boolean mSolved;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UUID getUU() {
        return UU;
    }

    public void setUU(UUID mId) {
        this.UU = mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Crime(){
        UU = UUID.randomUUID();
        mDate = Date.from(Instant.now());
    }

    public Crime(String title, Date date, boolean solved){
        UU = UUID.randomUUID();
        mTitle = title;
        mDate = date;
        mSolved = solved;
    }


}
