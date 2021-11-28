package com.example.studentcrimeapp;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static CrimeLab get(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }

        return sCrimeLab;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private CrimeLab(Context context){
        mCrimes = new ArrayList<>();
        for (int i = 0; i < 30; i++){
            Crime crime = new Crime();
            crime.setmTitle("Crime #" + i);
            crime.setmSolved(i % 2 == 0);
            mCrimes.add(crime);
        }
    }

    public List<Crime> getCrimes(){
        return mCrimes;
    }

    public int getCrimeLabSize() {return mCrimes.size();}
    public List<String> getCrimeNames(){
        List<String> crimeNames = new ArrayList<String>();
        for (Crime crime: mCrimes){
            crimeNames.add(crime.getmTitle());
        }
        return crimeNames;
    }

    public Crime getCrime(UUID id){
        for (Crime crime: mCrimes){
            if (crime.getmId().equals(id)){
                return crime;
            }
        }

        return null;
    }

    public Crime getCrime(int position) {
        if(position <= this.getCrimeLabSize() && position >= 0)
            return mCrimes.get(position);

        return null;
    }

    public void deleteCrime(UUID id){
        mCrimes.removeIf(crime -> crime.getmId() == id);
    }

    public void addCrime(Crime newCrime){
        mCrimes.add(newCrime);
    }

}
