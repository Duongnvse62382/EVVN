package com.king.evvn.presentation.login.viewmodel

import androidx.credentials.GetCredentialResponse

sealed class LoginIntent {
    object Logout : LoginIntent()
    class GoogleLogin(val response: GetCredentialResponse) : LoginIntent()
    class FailureLogin(val messageError : String) : LoginIntent()
}
