package com.example.countriescleanarchitecture.domain.usecase

import com.example.countriescleanarchitecture.domain.model.Country
import com.example.countriescleanarchitecture.presentation.state.CountryState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetFilteredUseCase {

    fun execute(countries: List<Country>, query: String): Flow<CountryState> = flow {
        try {
            emit(CountryState.Loading)
            val filteredCountries = countries.filter { it.name?.contains(query, ignoreCase = true) ?: false }
            emit(CountryState.Success(filteredCountries))
        } catch (e: Exception) {
            emit(CountryState.Error(e.message ?: "An error occurred"))
        }
    }
}