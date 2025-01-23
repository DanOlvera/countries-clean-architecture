package com.example.countriescleanarchitecture.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriescleanarchitecture.domain.usecase.GetCountriesUseCase
import com.example.countriescleanarchitecture.presentation.state.CountryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountryViewModel (private val getCountriesUseCase: GetCountriesUseCase): ViewModel() {

    private val _state = MutableStateFlow<CountryState>(CountryState.Loading)
    val state: StateFlow<CountryState> = _state

    fun fetchCountries() {
        viewModelScope.launch {
            getCountriesUseCase.execute().collect { emittedState ->
                _state.value = emittedState
            }
        }
    }
}