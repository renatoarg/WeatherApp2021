package br.com.renatoarg.weatherapp2021.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.renatoarg.weatherapp2021.MainActivity
import br.com.renatoarg.weatherapp2021.R
import br.com.renatoarg.weatherapp2021.databinding.FragmentSettingsBinding
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalUnsignedTypes
class SettingsFragment : Fragment(R.layout.fragment_settings), MavericksView {

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
        binding.darkThemeSwitch.setOnCheckedChangeListener { _, isChecked ->
            (activity as MainActivity).applyDarkTheme(isChecked)
        }
    }

    override fun invalidate() = withState(viewModel) { state ->

    }
}