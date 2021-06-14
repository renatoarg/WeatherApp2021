package br.com.renatoarg.weatherapp2021.home

import br.com.renatoarg.data.home.Timeline

sealed class HomeState {
    data class OnFetchTimelines(
        val result: List<Timeline>
    ) : HomeState()
}