package br.com.renatoarg.weatherapp2021.addplace

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.renatoarg.weatherapp2021.databinding.FragmentAddPlaceBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddPlaceFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAddPlaceBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddPlaceBinding.inflate(inflater, container, false)
        return binding.root
    }

}