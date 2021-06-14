package br.com.renatoarg.weatherapp2021.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import br.com.renatoarg.weatherapp2021.ErrorState
import br.com.renatoarg.weatherapp2021.LoadingState
import br.com.renatoarg.weatherapp2021.base.BaseFragment
import br.com.renatoarg.weatherapp2021.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

@ExperimentalUnsignedTypes
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override val viewModel: HomeViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeHomeState()
        observeErrorState()
        observeLoadingState()
        viewModel.fetchTimelines("-73.98529171943665,40.75872069597532")
        binding.textView.setOnClickListener {
            changeTheme()
        }
    }

    private fun observeHomeState() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.statesAsFlow.collect { homeState ->
                when (homeState) {
                    is HomeState.OnFetchTimelines -> onFetchTimelines(homeState)
                }
            }
        }
    }

    private fun observeErrorState() {
        viewModel.errorState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is ErrorState.OnError -> showToast(state.error)
                else -> {
                    // do nothing
                }
            }
        })
    }

    private fun showToast(error: String) {
        Toast.makeText(
            requireContext(),
            "OnError: $error",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun observeLoadingState() {
        viewModel.loadingState.observe(viewLifecycleOwner, { state ->
            when (state) {
                is LoadingState.OnLoading -> binding.loading.isVisible = true
                is LoadingState.OnIdle -> binding.loading.isVisible = false
            }
        })
    }

    private fun onFetchTimelines(homeState: HomeState.OnFetchTimelines) {
        Toast.makeText(
            requireContext(),
            "${homeState.result.size}",
            Toast.LENGTH_SHORT
        ).show()
    }
}