package com.king.evvn.presentation.splash.viewmodel

sealed interface SplashEvent {
    data object NavigateToLogin : SplashEvent
}
