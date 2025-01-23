package com.example.countriescleanarchitecture.domain.usecase

import com.example.countriescleanarchitecture.data.repository.CountryRepository
import com.example.countriescleanarchitecture.domain.model.Country

class GetCountriesUseCase(private val repository: CountryRepository) {
    suspend fun invoke(): Result<List<Country>> {
        return repository.getCountries()
    }
}