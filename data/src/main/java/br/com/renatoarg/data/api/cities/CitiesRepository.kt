package br.com.renatoarg.data.api.cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import br.com.renatoarg.data.database.Item
import br.com.renatoarg.data.database.ItemsRepository
import kotlinx.coroutines.flow.toList

class CitiesRepository(
    val database: ItemsRepository
) {

    fun fetchCities() : LiveData<List<Item>> {
        return database.allItems.asLiveData()
    }

}