package br.com.renatoarg.weatherapp2021.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    protected val loadingState = MutableLiveData<Boolean>()

    fun getLoadingState(): LiveData<Boolean> = loadingState

    private val errorState = MutableLiveData<ErrorState>()

    fun getErrorState(): LiveData<ErrorState> = errorState

    fun onError(error: String?) {
        errorState.postValue(ErrorState.OnError(error))
    }

    protected fun emitLoadingState(state: Boolean) {
        loadingState.postValue(state)
    }

    fun setErrorIdle() {
        errorState.value = ErrorState.OnIdle
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}