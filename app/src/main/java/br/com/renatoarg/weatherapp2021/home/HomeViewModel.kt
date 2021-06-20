package br.com.renatoarg.weatherapp2021.home

import br.com.renatoarg.data.BaseResponse
import br.com.renatoarg.data.NetworkHelper
import br.com.renatoarg.data.entity.WeatherForLocation
import br.com.renatoarg.data.home.HomeRepository
import br.com.renatoarg.weatherapp2021.HomeActivity
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@DelicateCoroutinesApi
@ExperimentalUnsignedTypes
class HomeViewModel(
    state: HomeState,
    private val repository: HomeRepository
) :  MavericksViewModel<HomeState>(state) {

    private fun updateWeatherForLocation(data: WeatherForLocation) = setState {
        copy(weatherForLocation = data)
    }

    private fun updateLoading(isLoading: Boolean) = setState {
        copy(isLoading = isLoading)
    }

    companion object : MavericksViewModelFactory<HomeViewModel, HomeState> {
        override fun create(viewModelContext: ViewModelContext, state: HomeState): HomeViewModel? {
            return HomeViewModel(state, viewModelContext.activity<HomeActivity>().repository)
        }
    }

    fun fetchWeatherForLocation(locationId: Int) {
        GlobalScope.launch {
            updateLoading(true)
            when (val response =
                NetworkHelper.makeApiCall { repository.fetchWeatherForLocation(locationId) }) {
                is BaseResponse.Success -> updateWeatherForLocation(response.data)
//                is BaseResponse.HttpError -> super.emitErrorState(ErrorState.OnHttpError(response.errorMessage))
//                is BaseResponse.Error -> super.emitErrorState(ErrorState.OnError(response.errorMessage))
            }
            updateLoading(false)
        }
    }
}