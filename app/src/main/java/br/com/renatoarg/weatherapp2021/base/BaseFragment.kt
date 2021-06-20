package br.com.renatoarg.weatherapp2021.base

import androidx.fragment.app.Fragment
import br.com.renatoarg.data.sharedPreferences.SharedPreferencesHelper
import org.koin.android.ext.android.inject

abstract class BaseFragment(layoutRes: Int) : Fragment(layoutRes) {

    val sharedPreferences: SharedPreferencesHelper by inject()

}