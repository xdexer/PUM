package com.example.lista8;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

interface CountryRestApi {
    @GET("all")
    Call<List<CountryApiModel>> loadCountries();
}
