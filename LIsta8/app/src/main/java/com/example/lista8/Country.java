package com.example.lista8;

import android.os.Build;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "country")
public class Country {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "country_name")
    private String mCountry;

    @ColumnInfo(name = "country_capital")
    private String mCapital;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return mCountry;
    }

    public void setCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getCapital() {
        return mCapital;
    }

    public void setCapital(String mCapital) {
        this.mCapital = mCapital;
    }

    public Country(String country, String capital){
        mCountry = country;
        mCapital = capital;
    }

}
