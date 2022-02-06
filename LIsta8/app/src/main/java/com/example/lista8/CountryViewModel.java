package com.example.lista8;

import android.app.Application;
import android.app.Presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class CountryViewModel extends ViewModel {
    LiveData<List<Country>> countryList;
    private CountryRepository repository;

    public CountryViewModel(Application app){
        repository = new CountryRepository(app);
        countryList = repository.getAllCountries();
    }

    public void fetchData(){
        repository.fetchData();
    }

    LiveData<List<Country>> getAllCountries(){
        return countryList;
    }
}
