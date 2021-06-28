package br.com.renatoarg.weatherapp2021.cities

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import br.com.renatoarg.data.database.Item
import br.com.renatoarg.weatherapp2021.R
import br.com.renatoarg.weatherapp2021.base.BaseFragment
import br.com.renatoarg.weatherapp2021.databinding.FragmentCitiesBinding
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalUnsignedTypes
class CitiesFragment(
    val callbackAddPlace: () -> Unit
) : BaseFragment(R.layout.fragment_cities), MavericksView {

    private var _binding: FragmentCitiesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CitiesViewModel by fragmentViewModel()

    private val adapter = CitiesAdapter() {
        Toast.makeText(requireContext(), "item: ${it.name}, ${it.id}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
        viewModel.fetchLocations()
    }

    private fun setupUi() {
        binding.apply {
            btnAddPlace.setOnClickListener {
                callbackAddPlace()
            }
            rvMyPlaces.adapter = adapter
        }
    }

    override fun invalidate()  = withState(viewModel) { state ->
        onInbalidte(state.locations)
    }

    private fun onInbalidte(locations: List<Item>?) {
        locations?.let {
            adapter.updateLocations(locations)
        }
    }
}