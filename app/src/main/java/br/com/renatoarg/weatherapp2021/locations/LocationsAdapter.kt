package br.com.renatoarg.weatherapp2021.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.renatoarg.data.api.home.entity.Location
import br.com.renatoarg.weatherapp2021.databinding.ItemLocationBinding

class LocationsAdapter(
    val callback: (location: Location) -> Unit
) : RecyclerView.Adapter<LocationsAdapter.BaseViewHolder<*>>() {

    private var locations = ArrayList<Location>()

    companion object {
        private const val TYPE_DEFAULT = 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_DEFAULT -> LocationViewHolder(
                parent,
                callback,
                ItemLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is LocationViewHolder -> holder.bind(locations[position])
        }
    }

    override fun getItemCount(): Int {
        return locations.size
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_DEFAULT
    }

    abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }

    fun updateLocations(locations: List<Location>) {
        this.locations.clear()
        this.locations.addAll(locations)
        notifyDataSetChanged()
    }
}