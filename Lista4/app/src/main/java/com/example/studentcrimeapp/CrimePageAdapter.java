package com.example.studentcrimeapp;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class CrimePageAdapter extends RecyclerView.Adapter<CrimePageAdapter.ViewHolder> {
    private CrimeLab mData;
    private static CrimePageAdapter sCrimeAdapter;
    private FragmentManager fragmentManager;
    private SwitchDateTimeDialogFragment dateTimeDialogFragment;

    public static CrimePageAdapter get(Context context, CrimeLab data, FragmentManager fragmentManager){
        if (sCrimeAdapter == null){
            sCrimeAdapter = new CrimePageAdapter(data, fragmentManager);
        }
        return sCrimeAdapter;
    }
    private CrimePageAdapter(CrimeLab data, FragmentManager fragmentManager) {
        this.mData = data;
        this.fragmentManager = fragmentManager;
        setDatePicker();
    }

    private void setDatePicker(){
        dateTimeDialogFragment = (SwitchDateTimeDialogFragment) fragmentManager.findFragmentByTag("TAG_DATETIME_FRAGMENT");
        if(dateTimeDialogFragment == null){
            dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance("Select Crime Date",
                    "OK",
                    "Cancel");
        }

        dateTimeDialogFragment.startAtCalendarView();
        dateTimeDialogFragment.set24HoursMode(true);
        dateTimeDialogFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        dateTimeDialogFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());
        dateTimeDialogFragment.setDefaultDateTime(new GregorianCalendar().getTime());
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_item, parent, false);
        return new ViewHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        System.out.println("DEBUG_DM: " + position);
        Crime currentCrime = this.mData.getCrime(position);
        holder.TextViewUUID.setText(currentCrime.getmId().toString());
        holder.editTextCrimeTitle.setText(currentCrime.getmTitle());
        holder.checkboxSolved.setChecked(currentCrime.ismSolved());

        holder.getEditTextCrimeTitle().addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("DEBUG_TITLE: " + currentCrime.getmTitle());
                currentCrime.setmTitle(holder.getEditTextCrimeTitle().getText().toString());
                System.out.println("DEBUG_TITLE: " + currentCrime.getmTitle());
            }
        });

        holder.getCheckboxSolved().setOnCheckedChangeListener((buttonView, isChecked) -> {
            System.out.println("DEBUG_EDITOR_CHECKED: " + currentCrime.ismSolved());
            currentCrime.setmSolved(isChecked);
            System.out.println("DEBUG_EDITOR_CHECKED: " + currentCrime.ismSolved());
        });
        holder.getButtonCrimeDate().setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                dateTimeDialogFragment.show(fragmentManager, "dialog_time");
            }
        });
        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                // Date is get on positive button click
                System.out.println("DEBUG_DATE_CHECKED: " + currentCrime.getmDate());
                currentCrime.setmDate(date);
                System.out.println("DEBUG_DATE_CHECKED: " + currentCrime.getmDate());
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Date is get on negative button click
            }
        });

        holder.buttonAddCrime.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Crime newCrime = new Crime(currentCrime.getmTitle(), currentCrime.getmDate(), currentCrime.ismSolved());
                mData.addCrime(newCrime);
                System.out.println("CRIME ADDED: " + mData.getCrimeLabSize());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mData.getCrimeLabSize();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView TextViewUUID;
        private final EditText editTextCrimeTitle;
        private final Button buttonCrimeDate;
        private final CheckBox checkboxSolved;
        private final Button buttonAddCrime;
        //private final Button buttonDeleteCrime;

        public TextView getTextViewUUID() {
            return TextViewUUID;
        }

        public EditText getEditTextCrimeTitle() {
            return editTextCrimeTitle;
        }

        public Button getButtonCrimeDate() {
            return buttonCrimeDate;
        }

        public CheckBox getCheckboxSolved() {
            return checkboxSolved;
        }



        @RequiresApi(api = Build.VERSION_CODES.O)
        public ViewHolder(View view) {
            super(view);
            this.TextViewUUID = view.findViewById(R.id.show_UUID);
            this.editTextCrimeTitle = view.findViewById(R.id.crime_title_edittext);
            this.buttonCrimeDate = view.findViewById(R.id.crime_date_button);
            this.checkboxSolved = view.findViewById(R.id.solved_checkbox);
            this.buttonAddCrime = view.findViewById(R.id.add_crime_button);
        }

    }

}
