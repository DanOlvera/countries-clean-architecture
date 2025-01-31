package com.example.countriescleanarchitecture.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriescleanarchitecture.domain.model.Country
import com.example.countriescleanarchitecture.domain.usecase.GetCountriesUseCase
import com.example.countriescleanarchitecture.presentation.state.CountryState
import com.example.countriescleanarchitecture.presentation.ui.UiListItem
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

    fun getUiListItem(countries: List<Country>) : List<UiListItem> {
        val uiList = mutableListOf<UiListItem>()
        //First character
        ///Store -> header
        //Iterate -> from until the first character changes
        //B ca new header and i
        //
        var previous: Char? = null
        for(c in countries) {
            val currentFirstLetter = c.name?.toCharArray()?.first()

            if (previous == null || previous != currentFirstLetter) {
                // add header
                uiList.add(UiListItem.HeaderListItem(currentFirstLetter.toString()))
            }
            // add country
            uiList.add(UiListItem.CountryListItem(c))
            previous = currentFirstLetter

        }

        return uiList
    }
}