package com.example.lista8;

import android.app.Application;
import android.service.controls.Control;

import androidx.lifecycle.LiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CountryRepository {
    private final CountryDao countryDao;
    private LiveData<List<Country>> countries;

    public CountryRepository(Application app){
        CountryDatabase database = CountryDatabase.getInstance(app);
        countryDao = database.countryDao();
        countries = countryDao.getAll();
    }

    public void fetchData(){
        CountryRestApi countryRestApi = Controller.getClient().create(CountryRestApi.class);
        Call<List<CountryApiModel>> call = countryRestApi.loadCountries();

        call.enqueue(new Callback<List<CountryApiModel>>() {
            @Override
            public void onResponse(Call<List<CountryApiModel>> call, Response<List<CountryApiModel>> response) {
                if (response.isSuccessful()) {
                    List<CountryApiModel> listFromAPI = response.body();
                    for (int i = 0; i < listFromAPI.size(); i++) {
                        String name = listFromAPI.get(i).getName() != null ? listFromAPI.get(i).getName().getCommon() : "None";
                        String capital = listFromAPI.get(i).getCapital() != null ? listFromAPI.get(i).getCapital().get(0) : "None";
                        Country newCountry = new Country(name, capital);

                        countryDao.insert(newCountry);
                    }
                    countries = countryDao.getAll();
                }
                else {
                    System.out.println("RESPONSE ERROR");
                }
            }

            @Override
            public void onFailure(Call<List<CountryApiModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public LiveData<List<Country>> getAllCountries(){
        return countries;
    }
}
