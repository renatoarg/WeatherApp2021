package br.com.renatoarg.weatherapp2021.locations

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import br.com.renatoarg.data.api.home.entity.Location
import br.com.renatoarg.weatherapp2021.R
import br.com.renatoarg.weatherapp2021.base.BaseActivity
import br.com.renatoarg.weatherapp2021.databinding.FragmentLocationsBinding
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.DelicateCoroutinesApi

@ExperimentalUnsignedTypes
@DelicateCoroutinesApi
class LocationsFragment : BottomSheetDialogFragment(), MavericksView {

    private var _binding: FragmentLocationsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LocationsViewModel by fragmentViewModel()

    private val adapter = LocationsAdapter { location ->
        viewModel.saveLocation(location)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: BottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(bottomSheetDialog)
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        setupSearchInput()
        setupSearchList()
    }

    private fun setupSearchList() {
        binding.rvSearchPlaces.adapter = adapter
    }

    private fun setupSearchInput() {
        binding.etSearch.addTextChangedListener { _editable ->
            _editable?.let { editable ->
                viewModel.queryLocations(editable.toString())
            }
        }
    }

    override fun invalidate() = withState(viewModel) { state ->
        onFetchLocations(state.locations)
        (activity as BaseActivity).onError(state.errorMessage)
        (activity as BaseActivity).onHttpError(state.httpErrorMessage)
    }

    private fun onFetchLocations(locations: List<Location>) {
        binding.apply {
            adapter.updateLocations(locations)
            rvSearchPlaces.isVisible = locations.isNotEmpty()
            tvEmptyLocations.isVisible = locations.isEmpty()
        }
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
        bottomSheet?.setBackgroundColor(Color.TRANSPARENT)
        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable = false
        behavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = getWindowHeight()
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        activity?.display?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

}