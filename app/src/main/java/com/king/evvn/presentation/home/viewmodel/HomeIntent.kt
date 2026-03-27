package com.king.evvn.presentation.home.viewmodel

sealed interface HomeIntent {
    data object Logout : HomeIntent
}
