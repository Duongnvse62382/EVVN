package com.king.evvn.presentation.login.viewmodel

sealed class LoginEvent {
    object LoginSuccess : LoginEvent()
    data class LoginFailure(val message: String) : LoginEvent()
}
