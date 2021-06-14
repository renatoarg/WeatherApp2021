package br.com.renatoarg.weatherapp2021.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.renatoarg.weatherapp2021.ErrorState
import br.com.renatoarg.weatherapp2021.LoadingState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

@kotlin.ExperimentalUnsignedTypes
abstract class BaseViewModel : ViewModel(), CoroutineScope {

    private val job: Job by lazy {
        Job()
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    val loadingState = MutableLiveData<LoadingState>()

    val errorState = MutableLiveData<ErrorState>()

    protected fun emitLoadingState(state: LoadingState) {
        loadingState.postValue(state)
    }

    protected fun emitErrorState(state: ErrorState) {
        errorState.postValue(state)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}