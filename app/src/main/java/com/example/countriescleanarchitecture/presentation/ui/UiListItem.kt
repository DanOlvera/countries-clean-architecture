package com.example.countriescleanarchitecture.presentation.ui

import com.example.countriescleanarchitecture.domain.model.Country

sealed interface UiListItem {
    data class CountryListItem(val country: Country): UiListItem
    data class HeaderListItem(val header: String): UiListItem
    // data class Footer
}