package br.com.renatoarg.weatherapp2021.base

import androidx.fragment.app.Fragment
import br.com.renatoarg.data.sharedPreferences.SharedPreferencesHelper
import br.com.renatoarg.weatherapp2021.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.android.ext.android.inject

abstract class BaseFragment(layoutRes: Int) : Fragment(layoutRes) {

    val sharedPreferences: SharedPreferencesHelper by inject()

    protected fun showDialog(title: String? = null, message: String? = null) {
        MaterialAlertDialogBuilder(requireContext(),
            R.style.ThemeOverlay_MaterialComponents)
            .setTitle(title ?: "")
            .setMessage(message ?: "")
            .setPositiveButton("Ok") { dialog, which ->
                // Respond to positive button press
            }
            .show()
    }

}