package br.com.renatoarg.weatherapp2021.home

import br.com.renatoarg.data.api.BaseResponse
import br.com.renatoarg.data.api.Constants.Companion.UNEXPECTED_ERROR
import br.com.renatoarg.data.api.NetworkHelper
import br.com.renatoarg.data.api.entity.WeatherForLocation
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

    private val homeSideEffectsChannel = Channel<HomeFragmentSideEffects>(Channel.BUFFERED)
    val homeSideEffects: Flow<HomeFragmentSideEffects> = homeSideEffectsChannel.receiveAsFlow()

    private val firedSideEffectEvents = ArrayList<HomeFragmentSideEffects>()

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
            return HomeViewModel(state, viewModelContext.activity<MainActivity>().repository)
        }
    }

    fun fetchWeatherForLocation(locationId: Int) {
        viewModelScope.launch {
            onLoading(true)
            when (val response =
                NetworkHelper.makeApiCall { repository.fetchWeatherForLocation(locationId) }) {
                is BaseResponse.Success -> onSuccess(response)
                is BaseResponse.Error -> onError(response.errorMessage)
                is BaseResponse.HttpError -> onHttpError(response.errorMessage)
            }
            onLoading(false)
        }
    }

    private suspend fun onSuccess(response: BaseResponse.Success<WeatherForLocation>) {
        onUpdateData(response.data)
        fireSideEffectEvent(HomeFragmentSideEffects.OnShowToast)
    }

    private suspend fun fireSideEffectEvent(event: HomeFragmentSideEffects) {
        if (!firedSideEffectEvents.contains(event)) {
            homeSideEffectsChannel.send(event)
            firedSideEffectEvents.add(event)
        }
    }
}