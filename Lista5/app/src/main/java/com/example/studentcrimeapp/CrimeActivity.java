package com.example.studentcrimeapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class CrimeActivity extends AppCompatActivity {

    TextView UUID_textview;
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

        UUID_textview = findViewById(R.id.show_UUID);
        UUID_textview.setText(CrimeID.toString());

        Title_textedit = findViewById(R.id.crime_title_edittext);
        Title_textedit.setText(currentCrime.getTitle());

        Solved_checkbox = findViewById(R.id.solved_checkbox);
        Solved_checkbox.setChecked(currentCrime.isSolved());

        CrimeDate_button = findViewById(R.id.crime_date_button);
        newDateFromPicker = currentCrime.getDate();
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void deleteButtonClicked(View view) {
        crimesList.deleteCrime(currentCrime.getUU());
        //delete record in DB (DELETE)
        class DeleteCrime extends AsyncTask<Void, Void, Void> {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Void doInBackground(Void... voids) {
                CrimeLab.get(getApplicationContext()).getCrimeDatabase().crimeDao().delete(currentCrime);
                System.out.println("DELETE CRIME " + currentCrime.getId());

                return null;
            }
        }
        DeleteCrime dc = new DeleteCrime();
        dc.execute();
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onPause(){
        super.onPause();
        currentCrime.setTitle(String.valueOf(((EditText) findViewById(R.id.crime_title_edittext)).getText()));
        currentCrime.setDate(newDateFromPicker);
        currentCrime.setSolved(((CheckBox) findViewById(R.id.solved_checkbox)).isChecked());

        //update record in DB
        class UpdateCrime extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                CrimeLab.get(getApplicationContext()).getCrimeDatabase().crimeDao().update(currentCrime);
                System.out.println("UPDATE CRIME " + currentCrime.getId());

                return null;
            }
        }
        UpdateCrime uc = new UpdateCrime();
        uc.execute();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addButtonClicked(View view) {
        Crime newCrime = new Crime(String.valueOf(((EditText) findViewById(R.id.crime_title_edittext)).getText()),
                newDateFromPicker,
                ((CheckBox) findViewById(R.id.solved_checkbox)).isChecked());
        crimesList.addCrime(newCrime);

        //Add new record to database
        class AddCrime extends AsyncTask<Void, Void, Void> {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected Void doInBackground(Void... voids) {
                CrimeLab.get(getApplicationContext()).getCrimeDatabase().crimeDao().insert(newCrime);
                System.out.println("ADD CRIME " + newCrime.getId());
                return null;
            }
        }
        AddCrime uc = new AddCrime();
        uc.execute();

        Toast toast = Toast.makeText(getApplicationContext(), "CRIME ADDED", Toast.LENGTH_SHORT);
        toast.show();

    }
}