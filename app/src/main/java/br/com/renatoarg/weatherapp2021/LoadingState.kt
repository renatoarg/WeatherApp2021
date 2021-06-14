package br.com.renatoarg.weatherapp2021

sealed class LoadingState {
    object OnIdle : LoadingState()
    object OnLoading : LoadingState()
}