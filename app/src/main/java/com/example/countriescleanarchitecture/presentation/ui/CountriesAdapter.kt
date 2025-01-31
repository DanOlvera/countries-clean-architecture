package com.example.countriescleanarchitecture.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.countriescleanarchitecture.databinding.CountriesHeaderBinding
import com.example.countriescleanarchitecture.databinding.CountriesItemBinding

class CountriesAdapter(private val countriesList: List<UiListItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_HEADER = 0
        const val VIEW_TYPE_COUNTRY = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            VIEW_TYPE_HEADER -> {
                val binding = CountriesHeaderBinding.inflate(inflater, parent, false)
                HeaderViewHolder(binding)
            }
            VIEW_TYPE_COUNTRY -> {
                val binding = CountriesItemBinding.inflate(inflater, parent, false)
                CountriesViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type. How?")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val country = countriesList[position]
        when(holder) {
            is CountriesViewHolder -> {
                val countryItem = (country as UiListItem.CountryListItem).country

                holder.binding.nameRegion.text ="${countryItem.name}, ${countryItem.region}"
                holder.binding.code.text = "${countryItem.code}"
                holder.binding.capital.text = "${countryItem.capital}"

            }
            is HeaderViewHolder -> {
                holder.binding.letter.text = (country as UiListItem.HeaderListItem).header
            }
        }
    }

    override fun getItemCount(): Int {
        return countriesList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (countriesList[position]) {
            is UiListItem.HeaderListItem -> VIEW_TYPE_HEADER
            is UiListItem.CountryListItem -> VIEW_TYPE_COUNTRY
        }
    }

    class HeaderViewHolder(val binding: CountriesHeaderBinding): RecyclerView.ViewHolder(binding.root)

    class CountriesViewHolder(val binding: CountriesItemBinding): RecyclerView.ViewHolder(binding.root)

}