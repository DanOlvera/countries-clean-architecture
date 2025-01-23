package com.example.countriescleanarchitecture.data.datasource

import com.example.countriescleanarchitecture.data.utils.ApiUtils
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CountryApiService {

    private val logging = HttpLoggingInterceptor().apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val okHttpClient = OkHttpClient.Builder().apply {
        addInterceptor(logging)
    }

    val apiService: CountryApi by lazy {
        Retrofit.Builder()
            .baseUrl(ApiUtils.BASE_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CountryApi::class.java)
    }
}