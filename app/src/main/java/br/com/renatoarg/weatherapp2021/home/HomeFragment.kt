package br.com.renatoarg.weatherapp2021.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import br.com.renatoarg.data.entity.WeatherForLocation
import br.com.renatoarg.weatherapp2021.ErrorState
import br.com.renatoarg.weatherapp2021.R
import br.com.renatoarg.weatherapp2021.base.BaseFragment
import br.com.renatoarg.weatherapp2021.databinding.FragmentHomeBinding
import coil.load
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@ExperimentalUnsignedTypes
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override val viewModel: HomeViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeHomeState()
        observeErrorState()
        observeLoadingState()
        viewModel.fetchWeatherForLocation(2487956)
    }

    private fun observeHomeState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.statesAsFlow.collect { homeState ->
                when (homeState) {
                    is HomeState.OnFetchWeatherForLocation -> onFetchWeatherForLocation(homeState.result)
                }
            }
        }
    }

    private fun observeErrorState() {
        viewModel.errorState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is ErrorState.OnError -> showToast(state.error)
                else -> {
                    // do nothing
                }
            }
        })
    }

    private fun showToast(error: String) {
        Toast.makeText(
            requireContext(),
            "OnError: $error",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun observeLoadingState() {
        viewModel.loadingState.observe(viewLifecycleOwner, { state ->
            when (state) {
//                is LoadingState.OnLoading -> binding.loading.isVisible = true
//                is LoadingState.OnIdle -> binding.loading.isVisible = false
            }
        })
    }

    private fun onFetchWeatherForLocation(weatherForLocation: WeatherForLocation) {
        binding.apply {
            citiesName.text = weatherForLocation.cityTitle
            pressureTextView.text = weatherForLocation.consolidatedWeather[0].airPressure.toString()
            humidityTextView.text = "${weatherForLocation.consolidatedWeather[0].humidity}%"
            windSpeedTextView.text = "${weatherForLocation.consolidatedWeather[0].windSpeed}m/s"
            temperatureTextView.text = "${weatherForLocation.consolidatedWeather[0].theTemp}"
            when (weatherForLocation.consolidatedWeather[0].weatherStateAbbr) {
                "lc" -> weatherImageView.load(R.drawable.ic_light_cloudy)
                else -> weatherImageView.load(R.drawable.ic_sun)
            }
        }
    }
}