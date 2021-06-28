package br.com.renatoarg.weatherapp2021.locations

import br.com.renatoarg.data.api.Response
import br.com.renatoarg.data.api.Constants.Companion.UNEXPECTED_ERROR
import br.com.renatoarg.data.api.APIHelper
import br.com.renatoarg.data.api.locations.LocationsRepository
import br.com.renatoarg.data.api.home.entity.Location
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
class LocationsViewModel(
    viewState: LocationsViewState,
    private val repository: LocationsRepository
) : MavericksViewModel<LocationsViewState>(viewState) {

    private val addPlaceSideEffectsChannel = Channel<LocationsSideEffects>(Channel.BUFFERED)
    val locationsSideEffects: Flow<LocationsSideEffects> = addPlaceSideEffectsChannel.receiveAsFlow()

    private val firedSideEffectEvents = ArrayList<LocationsSideEffects>()

    private fun updateLocations(locations: List<Location>) = setState {
        copy(locations = locations)
    }

    private fun showEmptyLocations() = setState {
        copy(locations = emptyList())
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

    companion object : MavericksViewModelFactory<LocationsViewModel, LocationsViewState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: LocationsViewState
        ): LocationsViewModel {
            return LocationsViewModel(state, viewModelContext.activity<MainActivity>().locationsRepository)
        }
    }

    fun queryLocations(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                showEmptyLocations()
                return@launch
            }
            onLoading(true)
            when (val response = APIHelper.callApi { repository.queryLocations(query) }) {
                is Response.Success -> onSuccess(response)
                is Response.Error -> onError(response.errorMessage)
                is Response.HttpError -> onHttpError(response.errorMessage)
            }
            onLoading(false)
        }
    }

    private fun onSuccess(response: Response.Success<List<Location>>) {
        updateLocations(response.data)
    }

    fun saveLocation(location: Location) {
        viewModelScope.launch {
            repository.saveLocation(location)
        }
    }

}