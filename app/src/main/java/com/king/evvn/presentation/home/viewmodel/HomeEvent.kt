package com.king.evvn.presentation.home.viewmodel

sealed interface HomeEvent {
    data object NavigateToLogin : HomeEvent
}
