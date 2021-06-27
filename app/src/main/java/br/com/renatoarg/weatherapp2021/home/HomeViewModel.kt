package br.com.renatoarg.weatherapp2021.home

import br.com.renatoarg.data.api.Response
import br.com.renatoarg.data.api.Constants.Companion.UNEXPECTED_ERROR
import br.com.renatoarg.data.api.APIHelper
import br.com.renatoarg.data.api.home.entity.WeatherForLocation
import br.com.renatoarg.data.api.home.HomeRepository
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
class HomeViewModel(
    viewState: HomeViewState,
    private val repository: HomeRepository
) : MavericksViewModel<HomeViewState>(viewState) {

    private val homeSideEffectsChannel = Channel<HomeSideEffects>(Channel.BUFFERED)
    val homeSideEffects: Flow<HomeSideEffects> = homeSideEffectsChannel.receiveAsFlow()

    private val firedSideEffectEvents = ArrayList<HomeSideEffects>()

    private fun onUpdateData(data: WeatherForLocation) = setState {
        copy(weatherForLocation = data)
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

    companion object : MavericksViewModelFactory<HomeViewModel, HomeViewState> {
        override fun create(
            viewModelContext: ViewModelContext,
            state: HomeViewState
        ): HomeViewModel {
            return HomeViewModel(state, viewModelContext.activity<MainActivity>().homeRepository)
        }
    }

    fun fetchWeatherForLocation(locationId: Int, refresh: Boolean = false) {
        viewModelScope.launch {
            onLoading(true)
            when (val response =
                APIHelper.callApi { repository.fetchWeatherForLocation(locationId, refresh) }) {
                is Response.Success -> onSuccess(response)
                is Response.Error -> onError(response.errorMessage)
                is Response.HttpError -> onHttpError(response.errorMessage)
            }
            onLoading(false)
        }
    }

    private suspend fun onSuccess(response: Response.Success<WeatherForLocation>) {
        onUpdateData(response.data)
        fireSideEffectEvent(HomeSideEffects.OnShowToast)
    }

    private suspend fun fireSideEffectEvent(event: HomeSideEffects) {
        if (!firedSideEffectEvents.contains(event)) {
            homeSideEffectsChannel.send(event)
            firedSideEffectEvents.add(event)
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            repository.getLocationId?.let { locationId ->
                fetchWeatherForLocation(locationId, refresh = true)
            }
        }
    }
}