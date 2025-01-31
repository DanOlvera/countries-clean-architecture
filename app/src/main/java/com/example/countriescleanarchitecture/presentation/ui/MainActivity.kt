package com.example.countriescleanarchitecture.presentation.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countriescleanarchitecture.data.datasource.CountryApiService
import com.example.countriescleanarchitecture.data.repository.CountryRepositoryImplementation
import com.example.countriescleanarchitecture.databinding.ActivityMainBinding
import com.example.countriescleanarchitecture.domain.usecase.GetCountriesUseCase
import com.example.countriescleanarchitecture.presentation.state.CountryState
import com.example.countriescleanarchitecture.presentation.viewmodel.CountryViewModel
import com.example.countriescleanarchitecture.presentation.viewmodel.CountryViewModelFactory
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: CountryViewModel by viewModels {
        val repository = CountryRepositoryImplementation(CountryApiService.apiService)
        val useCase = GetCountriesUseCase(repository)
        CountryViewModelFactory(useCase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                handleState(state)
            }
        }

        viewModel.fetchCountries()
    }

    private fun handleState(state: CountryState) {
        when(state) {
            is CountryState.Loading -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.countriesList.visibility = View.GONE
                binding.errorMessage.visibility = View.GONE
            }
            is CountryState.Success -> {
                binding.progressBar.visibility = View.GONE
                binding.countriesList.visibility = View.VISIBLE
                binding.errorMessage.visibility = View.GONE

                val countriesList = viewModel.getUiListItem(state.countries)
                binding.countriesList.adapter = CountriesAdapter(countriesList)
            }
            is CountryState.Error -> {
                binding.progressBar.visibility = View.GONE
                binding.countriesList.visibility = View.GONE
                binding.errorMessage.visibility = View.VISIBLE

                binding.errorMessage.text = state.errorMessage
            }
        }
    }

    private fun setupRecyclerView() {
        binding.countriesList.layoutManager = LinearLayoutManager(this)
    }
}