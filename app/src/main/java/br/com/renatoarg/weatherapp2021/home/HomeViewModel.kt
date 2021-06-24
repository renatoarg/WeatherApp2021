package br.com.renatoarg.weatherapp2021.home

import androidx.lifecycle.viewModelScope
import br.com.renatoarg.data.BaseResponse
import br.com.renatoarg.data.NetworkHelper
import br.com.renatoarg.data.entity.WeatherForLocation
import br.com.renatoarg.data.home.HomeRepository
import br.com.renatoarg.weatherapp2021.ErrorState
import br.com.renatoarg.weatherapp2021.base.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container

@ExperimentalUnsignedTypes
class HomeViewModel constructor(
    private val repository: HomeRepository
) : ContainerHost<HomeState, HomeSideEffect>, BaseViewModel() {

    // Include `orbit-viewmodel` for the factory function
    override val container =
        container<HomeState, HomeSideEffect>(
            HomeState(
                weatherForLocation = null,
                isLoading = false
            )
        )

    private fun update(data: WeatherForLocation?) = intent {
        postSideEffect(HomeSideEffect.Toast("Adding: ${state.weatherForLocation?.consolidatedWeather?.size}!"))

        reduce {
            state.copy(weatherForLocation = data)
        }
    }

    private fun showLoading(isLoading: Boolean) = intent {
        reduce {
            state.copy(isLoading = isLoading)
        }
    }

    fun fetchWeatherForLocation(locationId: Int) {
        launch {
            showLoading(true)
            when (val response =
                NetworkHelper.makeApiCall { repository.fetchWeatherForLocation(locationId) }) {
                is BaseResponse.Success -> update(response.data)
                is BaseResponse.HttpError -> super.emitErrorState(ErrorState.OnHttpError(response.errorMessage))
                is BaseResponse.Error -> super.emitErrorState(ErrorState.OnError(response.errorMessage))
            }
            showLoading(false)
        }
    }
}

data class HomeState(
    val weatherForLocation: WeatherForLocation? = null,
    val isLoading: Boolean = false
)

sealed class HomeSideEffect {
    data class Toast(val text: String) : HomeSideEffect()
}