package com.example.countriescleanarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriescleanarchitecture.domain.model.Country
import com.example.countriescleanarchitecture.domain.usecase.GetCountriesUseCase
import com.example.countriescleanarchitecture.domain.usecase.GetFilteredUseCase
import com.example.countriescleanarchitecture.presentation.state.CountryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountryViewModel (
    private val getCountriesUseCase: GetCountriesUseCase,
    private val getFilteredUseCase: GetFilteredUseCase
): ViewModel() {

    private val _state = MutableStateFlow<CountryState>(CountryState.Loading)
    val state: StateFlow<CountryState> = _state

    private var allCountries = emptyList<Country>()

    fun fetchCountries() {
        viewModelScope.launch {
            getCountriesUseCase.execute().collect { emittedState ->
                when(emittedState) {
                    is CountryState.Success -> {
                        allCountries = emittedState.countries
                        _state.value = emittedState
                    }
                    else -> {
                        _state.value = emittedState
                    }
                }
            }
        }
    }

    fun filterCountries(query: String) {
        viewModelScope.launch {
            getFilteredUseCase.execute(allCountries, query).collect { filtered ->
                try {
                    _state.value = filtered
                } catch (e: Exception) {
                    CountryState.Error(e.message ?: "An error occurred")
                }
            }
        }
    }
}