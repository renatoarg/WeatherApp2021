package br.com.renatoarg.weatherapp2021.home

sealed class HomeSideEffects {
    object OnShowToast : HomeSideEffects()
    object OnShowToast2 : HomeSideEffects()
}