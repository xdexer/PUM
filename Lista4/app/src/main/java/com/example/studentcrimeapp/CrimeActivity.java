package com.example.studentcrimeapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class CrimeActivity extends AppCompatActivity {

    CrimeLab crimesList;
    Date newDateFromPicker;
    int crimePosition;
    CrimePageAdapter crimePageAdapter;
    ViewPager2 viewPager2;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);
        Intent intent = getIntent();
        UUID CrimeID = UUID.fromString(intent.getStringExtra("crimeID"));
        System.out.println(CrimeID);
        //currentCrime = crimesList.getCrime(CrimeID);

        crimePosition = intent.getIntExtra("position", 0);
        System.out.println("DEBUG_POSITION: " + crimePosition);

        viewPager2 = findViewById(R.id.crime_viewpager);
        crimesList = CrimeLab.get(this);
        crimePageAdapter = CrimePageAdapter.get(this, crimesList, getSupportFragmentManager());
        viewPager2.setAdapter(crimePageAdapter);
        viewPager2.setCurrentItem(crimePosition, false);
    }

    public void deleteButtonClicked(View view) {
        //I know it is terrible and should be done in viewPager
        crimesList.deleteCrime(crimesList.getCrime(crimePosition).getmId());
        finish();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    public void FirstCrime(View view){
        viewPager2.setCurrentItem(0, false);
    }

    public void LastCrime(View view){
        viewPager2.setCurrentItem(crimesList.getCrimeLabSize(), false);
    }
}
