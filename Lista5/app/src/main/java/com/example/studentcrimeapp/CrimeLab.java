package com.example.studentcrimeapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class CrimeLab{
    private static CrimeLab sCrimeLab;

    private List<Crime> mCrimes;
    private CrimeDatabase crimeDatabase;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static CrimeLab get(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }

        return sCrimeLab;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private CrimeLab(Context context){
        crimeDatabase = CrimeDatabase.getInstance(context);
        mCrimes = crimeDatabase.crimeDao().getAll();

        //Create initial data for DB (use only if not already created) (CREATE)
//        class SaveCrime extends AsyncTask<Void, Void, Void> {
//
//            @Override
//            protected Void doInBackground(Void... voids){
//                for (int i = 0; i < 30; i++){
//                    Crime crime = new Crime();
//                    crime.setTitle("Crime #" + i);
//                    crime.setSolved(i % 2 == 0);
//
//                    CrimeLab.get(context).getCrimeDatabase().crimeDao().insert(crime);
//                }
//                return null;
//            }
//        }
//        SaveCrime sc = new SaveCrime();
//        sc.execute();

        //get data from DB (READ) NOT WORKING BECAUSE OF SYNC THREADS
//        class GetCrimes extends AsyncTask<Void, Void, Void> {
//            @Override
//            protected Void doInBackground(Void... voids) {
//                mCrimes = CrimeLab.get(context).getCrimeDatabase().crimeDao().getAll();
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid){
//                super.onPostExecute(aVoid);
//            }
//        }
//        GetCrimes gc = new GetCrimes();
//        gc.execute();

    }

    public List<Crime> getCrimes(){
        return mCrimes;
    }

    public CrimeDatabase getCrimeDatabase(){
        return crimeDatabase;
    }

    public int getCrimeLabSize() {return mCrimes.size();}
    public List<String> getCrimeNames(){
        List<String> crimeNames = new ArrayList<String>();
        for (Crime crime: mCrimes){
            crimeNames.add(crime.getTitle());
        }
        return crimeNames;
    }

    public Crime getCrime(UUID id){
        for (Crime crime: mCrimes){
            if (crime.getUU().equals(id)){
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
        mCrimes.removeIf(crime -> crime.getUU() == id);
    }

    public void addCrime(Crime newCrime){
        mCrimes.add(newCrime);
    }

}
