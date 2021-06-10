package br.com.renatoarg.weatherapp2021.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.renatoarg.weatherapp2021.R

@ExperimentalUnsignedTypes
abstract class BaseFragment : Fragment() {

    protected abstract val viewModel: BaseViewModel?

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}