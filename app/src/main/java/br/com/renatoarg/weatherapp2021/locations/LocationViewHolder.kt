package br.com.renatoarg.weatherapp2021.locations

import android.view.ViewGroup
import br.com.renatoarg.data.api.home.entity.Location
import br.com.renatoarg.weatherapp2021.databinding.ItemLocationBinding

class LocationViewHolder(
    val parent: ViewGroup,
    val callback: (location: Location) -> Unit,
    private val binding: ItemLocationBinding
) : LocationsAdapter.BaseViewHolder<Location>(binding.root) {

    override fun bind(item: Location) {
        binding.apply {
            tvLocationName.text = item.cityTitle
            wrapper.setOnClickListener {
                callback(item)
            }
        }
    }
}