package br.com.renatoarg.weatherapp2021.cities

import android.view.ViewGroup
import br.com.renatoarg.data.api.home.entity.Location
import br.com.renatoarg.data.database.Item
import br.com.renatoarg.weatherapp2021.databinding.ItemLocationBinding

class CityViewHolder(
    val parent: ViewGroup,
    val callback: (location: Item) -> Unit,
    private val binding: ItemLocationBinding
) : CitiesAdapter.BaseViewHolder<Item>(binding.root) {

    override fun bind(item: Item) {
        binding.apply {
            tvLocationName.text = item.name
            wrapper.setOnClickListener {
                callback(item)
            }
        }
    }
}