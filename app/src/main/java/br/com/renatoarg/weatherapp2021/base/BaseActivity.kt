package br.com.renatoarg.weatherapp2021.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import br.com.renatoarg.data.sharedPreferences.SharedPreferencesHelper
import br.com.renatoarg.weatherapp2021.settings.SettingsConstants.DARK_MODE_PREF
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity() {

    private val sharedPreferences: SharedPreferencesHelper by inject()

    fun applyDarkTheme(isDarkThemeEnabled: Boolean = false) {
        when (isDarkThemeEnabled) {
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
    }

    override fun onResume() {
        super.onResume()
        applyDarkTheme(sharedPreferences.readBoolean(DARK_MODE_PREF))
    }
}