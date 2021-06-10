package br.com.renatoarg.weatherapp2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import br.com.renatoarg.weatherapp2021.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalUnsignedTypes
class HomeFragment : BaseFragment() {

    override val viewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}