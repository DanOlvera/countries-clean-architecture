package com.example.countriescleanarchitecture.presentation.state

import com.example.countriescleanarchitecture.domain.model.Country

sealed class CountryState {
    data object Loading: CountryState()
    data class Success(val countries: List<Country>): CountryState()
    data class Error(val errorMessage: String): CountryState()
}