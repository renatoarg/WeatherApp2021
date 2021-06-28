package br.com.renatoarg.weatherapp2021.cities

import br.com.renatoarg.data.api.Response
import br.com.renatoarg.data.api.Constants.Companion.UNEXPECTED_ERROR
import br.com.renatoarg.data.api.APIHelper
import br.com.renatoarg.data.api.cities.CitiesRepository
import br.com.renatoarg.data.api.home.entity.WeatherForLocation
import br.com.renatoarg.data.api.home.HomeRepository
import br.com.renatoarg.data.api.home.entity.Location
import br.com.renatoarg.data.database.Item
import br.com.renatoarg.weatherapp2021.MainActivity
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@ExperimentalUnsignedTypes
class CitiesViewModel(
    viewState: CitiesViewState,
    private val repository: CitiesRepository
) : MavericksViewModel<CitiesViewState>(viewState) {

    private fun onUpdateData(data: List<Item>) = setState {
        copy(locations = data)
    }

    private fun onLoading(isLoading: Boolean) = setState {
        copy(isLoading = isLoading)
    }

    private fun onHttpError(httpErrorMessage: String?) = setState {
        copy(httpErrorMessage = httpErrorMessage ?: UNEXPECTED_ERROR)
    }

    private fun onError(errorMessage: String?) = setState {
        copy(errorMessage = errorMessage ?: UNEXPECTED_ERROR)
    }

    companion object : MavericksViewModelFactory<CitiesViewModel, CitiesViewState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: CitiesViewState
        ): CitiesViewModel {
            return CitiesViewModel(state, viewModelContext.activity<MainActivity>().citiesRepository)
        }
    }

    fun fetchLocations() {
        viewModelScope.launch {
            onLoading(true)
            repository.fetchCities().observeForever {
                onUpdateData(it)
            }
            onLoading(false)
        }
    }

}