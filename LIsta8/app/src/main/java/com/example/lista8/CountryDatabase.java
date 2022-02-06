package com.example.lista8;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Country.class}, version = 2)
public abstract class CountryDatabase extends RoomDatabase {
    private static CountryDatabase instance;

    public static synchronized CountryDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context, CountryDatabase.class, "CountryDatabase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract CountryDao countryDao();
}
