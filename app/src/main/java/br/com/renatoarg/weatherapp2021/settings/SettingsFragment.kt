package br.com.renatoarg.weatherapp2021.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.renatoarg.weatherapp2021.base.BaseFragment
import br.com.renatoarg.weatherapp2021.base.BaseViewModel
import br.com.renatoarg.weatherapp2021.databinding.FragmentSettingsBinding

@ExperimentalUnsignedTypes
class SettingsFragment : BaseFragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override val viewModel: BaseViewModel?
        get() = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }
}