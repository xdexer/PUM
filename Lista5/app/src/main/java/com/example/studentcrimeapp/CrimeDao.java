package com.example.studentcrimeapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CrimeDao {
    @Query("SELECT * FROM crime")
    List<Crime> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Crime crime);

    @Delete
    void delete(Crime crime);

    @Update
    void update(Crime crime);
}
