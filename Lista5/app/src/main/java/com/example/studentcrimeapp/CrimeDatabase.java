package com.example.studentcrimeapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Crime.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class CrimeDatabase extends RoomDatabase {
    private static CrimeDatabase instance;

    public static synchronized CrimeDatabase getInstance(Context context) {
        if (instance == null){
            instance = Room.databaseBuilder(context, CrimeDatabase.class, "CrimeDatabase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract CrimeDao crimeDao();
}
