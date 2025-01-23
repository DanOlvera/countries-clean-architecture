package com.example.countriescleanarchitecture.domain.usecase

import com.example.countriescleanarchitecture.data.repository.CountryRepository
import com.example.countriescleanarchitecture.presentation.state.CountryState
import kotlinx.coroutines.flow.Flow

class GetCountriesUseCase(private val repository: CountryRepository) {

    suspend fun execute(): Flow<CountryState> = repository.getCountries()
}