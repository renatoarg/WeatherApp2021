package br.com.renatoarg.weatherapp2021

import br.com.renatoarg.weatherapp2021.base.BaseViewModel
import br.com.renatoarg.weatherapp2021.data.HomeRepository
import javax.inject.Inject

@ExperimentalUnsignedTypes
class HomeViewModel @Inject constructor(
    repository: HomeRepository
) : BaseViewModel()