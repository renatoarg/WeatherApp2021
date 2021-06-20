package br.com.renatoarg.weatherapp2021.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.renatoarg.weatherapp2021.MainActivity
import br.com.renatoarg.weatherapp2021.R
import br.com.renatoarg.weatherapp2021.base.BaseFragment
import br.com.renatoarg.weatherapp2021.databinding.FragmentSettingsBinding
import br.com.renatoarg.weatherapp2021.settings.SettingsConstants.DARK_MODE_PREF
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalUnsignedTypes
class SettingsFragment : BaseFragment(R.layout.fragment_settings), MavericksView {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by fragmentViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.darkThemeSwitch.apply {
            isChecked = sharedPreferences.readBoolean(DARK_MODE_PREF)
            setOnCheckedChangeListener { _, isChecked ->
                (activity as MainActivity).applyDarkTheme(isChecked)
                sharedPreferences.write(DARK_MODE_PREF, isChecked)
            }
        }
    }

    override fun invalidate() = withState(viewModel) { state ->

    }
}