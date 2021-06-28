package br.com.renatoarg.weatherapp2021.cities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.renatoarg.data.api.home.entity.Location
import br.com.renatoarg.data.database.Item
import br.com.renatoarg.weatherapp2021.databinding.ItemLocationBinding

class CitiesAdapter(
    val callback: (item: Item) -> Unit
) : RecyclerView.Adapter<CitiesAdapter.BaseViewHolder<*>>() {

    private var cities = ArrayList<Item>()

    companion object {
        private const val TYPE_DEFAULT = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_DEFAULT -> CityViewHolder(
                parent,
                callback,
                ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is CityViewHolder -> holder.bind(cities[position])
        }
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_DEFAULT
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    fun updateLocations(cities: List<Item>) {
        this.cities.clear()
        this.cities.addAll(cities)
        notifyDataSetChanged()
    }
}