package com.example.countriescleanarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.countriescleanarchitecture.domain.usecase.GetCountriesUseCase

class CountryViewModelFactory(
    private val getCountriesUseCase: GetCountriesUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
            return CountryViewModel(getCountriesUseCase) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}