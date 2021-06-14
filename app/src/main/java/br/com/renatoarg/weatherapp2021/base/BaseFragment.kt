package br.com.renatoarg.weatherapp2021.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import br.com.renatoarg.weatherapp2021.ErrorState
import br.com.renatoarg.weatherapp2021.R

@ExperimentalUnsignedTypes
abstract class BaseFragment : Fragment() {

    protected abstract val viewModel: BaseViewModel?

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel?.errorState?.observe(viewLifecycleOwner, { state ->
            if (state is ErrorState.OnHttpError) {
                showErrorAlert(state.error)
            }
        })
    }

    private fun showErrorAlert(
        message: String?,
        title: String? = null,
        callback: (() -> Unit)? = null
    ) {
        activity?.let { context ->
            showAlert(context, title, message, callback)
        }
    }

    private fun showAlert(
        context: FragmentActivity,
        title: String?,
        message: String?,
        callback: (() -> Unit)?
    ) {
        AlertDialog.Builder(context)
            .setTitle(title ?: getString(R.string.error))
            .setMessage(message ?: getString(R.string.unexpectedError))
            .setPositiveButton(R.string.ok) { _, _ ->
                callback?.invoke()
            }
            .create()
            .show()
    }

    fun changeTheme() {
        activity?.let {
            (it as BaseActivity).changeTheme()
        }
    }
}