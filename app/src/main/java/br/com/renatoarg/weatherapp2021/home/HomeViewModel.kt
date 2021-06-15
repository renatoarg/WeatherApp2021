package br.com.renatoarg.weatherapp2021.home

import br.com.renatoarg.data.BaseResponse
import br.com.renatoarg.data.NetworkHelper
import br.com.renatoarg.data.home.HomeRepository
import br.com.renatoarg.weatherapp2021.ErrorState
import br.com.renatoarg.weatherapp2021.LoadingState
import br.com.renatoarg.weatherapp2021.base.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@ExperimentalUnsignedTypes
class HomeViewModel constructor(
    private val repository: HomeRepository
) : BaseViewModel() {

    private val statesChannel = Channel<HomeState>()
    val statesAsFlow = statesChannel.receiveAsFlow()

    fun fetchWeatherForLocation(locationId: Int) {
        launch {
            super.emitLoadingState(LoadingState.OnLoading)
            when (val response =
                NetworkHelper.makeApiCall { repository.fetchWeatherForLocation(locationId) }) {
                is BaseResponse.Success -> statesChannel.send(HomeState.OnFetchWeatherForLocation(response.data))
                is BaseResponse.HttpError -> super.emitErrorState(ErrorState.OnHttpError(response.errorMessage))
                is BaseResponse.Error -> super.emitErrorState(ErrorState.OnError(response.errorMessage))
            }
            super.emitLoadingState(LoadingState.OnIdle)
        }
    }
}