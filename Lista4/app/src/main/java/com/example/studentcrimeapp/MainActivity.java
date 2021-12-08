package com.example.studentcrimeapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume(){
        super.onResume();
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void init(){
        setContentView(R.layout.activity_main);
        CrimeLab crimesList = CrimeLab.get(this);
        RecyclerView recyclerView = findViewById(R.id.rvCrimes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(CrimeAdapter.get(this, crimesList));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}