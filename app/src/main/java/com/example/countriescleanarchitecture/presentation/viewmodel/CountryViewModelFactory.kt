package com.example.countriescleanarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.countriescleanarchitecture.domain.usecase.GetCountriesUseCase
import com.example.countriescleanarchitecture.domain.usecase.GetFilteredUseCase

class CountryViewModelFactory(
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getFilteredUseCase: GetFilteredUseCase
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CountryViewModel::class.java)) {
            return CountryViewModel(getCountriesUseCase, getFilteredUseCase) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}