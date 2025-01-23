package com.example.countriescleanarchitecture.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countriescleanarchitecture.databinding.CountriesItemBinding
import com.example.countriescleanarchitecture.domain.model.Country

class CountriesAdapter(private val countriesList: List<Country>): RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder>() {
    class CountriesViewHolder(private val binding: CountriesItemBinding):
        RecyclerView.ViewHolder(binding.root) {

            fun bind(country: Country) {
                binding.nameRegion.text = "${country.name}, ${country.region}"
                binding.code.text = country.code
                binding.capital.text = country.capital
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountriesViewHolder {
        val binding = CountriesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CountriesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }

    override fun onBindViewHolder(holder: CountriesViewHolder, position: Int) {
        holder.bind(countriesList[position])
    }
}