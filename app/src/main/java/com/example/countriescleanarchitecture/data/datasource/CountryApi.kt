package com.example.countriescleanarchitecture.data.datasource

import com.example.countriescleanarchitecture.domain.model.Country
import retrofit2.http.GET

interface CountryApi {
    @GET("countries.json")
    suspend fun getCountries(): List<Country>
}