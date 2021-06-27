package br.com.renatoarg.weatherapp2021.settings

import br.com.renatoarg.data.api.home.HomeRepository
import br.com.renatoarg.weatherapp2021.MainActivity
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalUnsignedTypes
class SettingsViewModel(
    state: SettingsState,
    private val repository: HomeRepository
) :  MavericksViewModel<SettingsState>(state) {

    private fun updateDarkMode(isDarkModeEnabled: Boolean) = setState {
        copy(isDarkModeEnabled = isDarkModeEnabled)
    }

    companion object : MavericksViewModelFactory<SettingsViewModel, SettingsState> {
        override fun create(viewModelContext: ViewModelContext, state: SettingsState): SettingsViewModel? {
            return SettingsViewModel(state, viewModelContext.activity<MainActivity>().homeRepository)
        }
    }
}