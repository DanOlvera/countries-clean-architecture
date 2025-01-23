package com.example.countriescleanarchitecture.data.repository

import com.example.countriescleanarchitecture.data.datasource.CountryApi
import com.example.countriescleanarchitecture.presentation.state.CountryState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CountryRepositoryImplementation(private val countryApi: CountryApi): CountryRepository {

    override suspend fun getCountries(): Flow<CountryState> = flow {
        emit(CountryState.Loading)
        try {
            val response = countryApi.getCountries()
            emit(CountryState.Success(response))
        } catch (e: Exception) {
            emit(CountryState.Error(e.message ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}