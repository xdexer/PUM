package com.example.studentcrimeapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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

    EditText UUID_textedit;
    EditText Title_textedit;
    CheckBox Solved_checkbox;
    Button CrimeDate_button;
    CrimeLab crimesList;
    Crime currentCrime;
    Date newDateFromPicker;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        crimesList = CrimeLab.get(this);
        Intent intent = getIntent();
        UUID CrimeID = UUID.fromString(intent.getStringExtra("crimeID"));
        currentCrime = crimesList.getCrime(CrimeID);

        UUID_textedit = findViewById(R.id.show_UUID);
        UUID_textedit.setText(CrimeID.toString());

        Title_textedit = findViewById(R.id.crime_title_edittext);
        Title_textedit.setText(currentCrime.getmTitle());

        Solved_checkbox = findViewById(R.id.solved_checkbox);
        Solved_checkbox.setChecked(currentCrime.ismSolved());

        CrimeDate_button = findViewById(R.id.crime_date_button);
        newDateFromPicker = currentCrime.getmDate();
    }

    public void dateButtonClicked(View view) {
        SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance("Select Crime Date",
                "OK",
                "Cancel");

        dateTimeDialogFragment.startAtCalendarView();
        dateTimeDialogFragment.set24HoursMode(true);
        dateTimeDialogFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        dateTimeDialogFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());
        dateTimeDialogFragment.setDefaultDateTime(new GregorianCalendar().getTime());

        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                // Date is get on positive button click
                newDateFromPicker = date;
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Date is get on negative button click
            }
        });

        dateTimeDialogFragment.show(getSupportFragmentManager(), "dialog_time");
    }


    public void deleteButtonClicked(View view) {
        crimesList.deleteCrime(currentCrime.getmId());
        finish();
    }

    @Override
    public void onPause(){
        super.onPause();
        currentCrime.setmId(UUID.fromString(String.valueOf(((EditText) findViewById(R.id.show_UUID)).getText())));
        currentCrime.setmTitle(String.valueOf(((EditText) findViewById(R.id.crime_title_edittext)).getText()));
        currentCrime.setmDate(newDateFromPicker);
        currentCrime.setmSolved(((CheckBox) findViewById(R.id.solved_checkbox)).isChecked());
    }

    public void addButtonClicked(View view) {
        Crime newCrime = new Crime(UUID.fromString(String.valueOf(((EditText) findViewById(R.id.show_UUID)).getText())),
                String.valueOf(((EditText) findViewById(R.id.crime_title_edittext)).getText()),
                newDateFromPicker,
                ((CheckBox) findViewById(R.id.solved_checkbox)).isChecked());
        crimesList.addCrime(newCrime);
    }
}