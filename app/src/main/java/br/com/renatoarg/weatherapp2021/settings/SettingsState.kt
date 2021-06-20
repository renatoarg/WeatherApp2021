package br.com.renatoarg.weatherapp2021.settings

import com.airbnb.mvrx.MavericksState

data class SettingsState(
    val isDarkModeEnabled: Boolean = false,
) : MavericksState