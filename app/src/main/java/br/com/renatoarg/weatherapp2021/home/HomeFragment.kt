package br.com.renatoarg.weatherapp2021.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import br.com.renatoarg.data.api.home.entity.WeatherForLocation
import br.com.renatoarg.weatherapp2021.R
import br.com.renatoarg.weatherapp2021.base.BaseFragment
import br.com.renatoarg.weatherapp2021.databinding.FragmentHomeBinding
import coil.load
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.flow.collect

@DelicateCoroutinesApi
@ExperimentalUnsignedTypes
class HomeFragment : BaseFragment(R.layout.fragment_home), MavericksView {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by fragmentViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchWeatherForLocation()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.homeSideEffects.collect { sideEffect ->
                when (sideEffect) {
                    is HomeSideEffects.OnShowToast -> Toast.makeText(
                        requireContext(),
                        "Unique toast",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
        setupUi()
    }

    private fun setupUi() {
        binding.apply{
            swiperefresh.setOnRefreshListener {
                viewModel.onRefresh()
            }
        }
    }

    private fun onFetchWeatherForLocation(weatherForLocation: WeatherForLocation?) {
        weatherForLocation?.let {
            binding.apply {
                citiesName.text = it.cityTitle
                pressureTextView.text = it.consolidatedWeather[0].airPressure.toString()
                humidityTextView.text = "${it.consolidatedWeather[0].humidity}%"
                windSpeedTextView.text = "${it.consolidatedWeather[0].windSpeed}m/s"
                temperatureTextView.text = "${it.consolidatedWeather[0].theTemp}"
                when (it.consolidatedWeather[0].weatherStateAbbr) {
                    "lc" -> weatherImageView.load(R.drawable.ic_light_cloudy)
                    else -> weatherImageView.load(R.drawable.ic_sun)
                }
                swiperefresh.isRefreshing = false
            }
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        onFetchWeatherForLocation(state.weatherForLocation)
        onUpdateLoading(state.isLoading)
        onLocationNotSet(state.isLocationSet)
        onError(state.errorMessage)
        onHttpError(state.httpErrorMessage)
    }

    private fun onLocationNotSet(locationSet: Boolean) {
        if (!locationSet) {
            binding.homeViewsGroup.isVisible = false
            showDialog("No location", "Location not set")
        }
    }

    private fun onError(errorMessage: String?) {
        errorMessage?.let {
            binding.swiperefresh.isRefreshing = false
            showDialog("Error", it)
        }
    }

    private fun onHttpError(httpErrorMessage: String?) {
        httpErrorMessage?.let {
            binding.swiperefresh.isRefreshing = false
            showDialog("Error", it)
        }
    }

    private fun onUpdateLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
        binding.homeViewsGroup.isVisible = !isLoading
    }
}