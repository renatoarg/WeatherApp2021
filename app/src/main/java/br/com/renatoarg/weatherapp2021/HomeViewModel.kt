package br.com.renatoarg.weatherapp2021

import br.com.renatoarg.weatherapp2021.base.BaseViewModel
import br.com.renatoarg.weatherapp2021.data.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalUnsignedTypes
@HiltViewModel
class HomeViewModel @Inject constructor(
    repository: HomeRepository
) : BaseViewModel()