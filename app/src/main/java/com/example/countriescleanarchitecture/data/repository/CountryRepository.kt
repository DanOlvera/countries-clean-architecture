package com.example.countriescleanarchitecture.data.repository

import com.example.countriescleanarchitecture.presentation.state.CountryState
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    suspend fun getCountries(): Flow<CountryState>
}