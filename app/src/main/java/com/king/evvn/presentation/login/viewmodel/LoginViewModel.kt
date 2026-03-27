package com.king.evvn.presentation.login.viewmodel

import android.util.Log
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.king.evvn.domain.model.GoogleCredentialData
import com.king.evvn.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    private val _event = MutableSharedFlow<LoginEvent>()
    val event: SharedFlow<LoginEvent> = _event

    init {
        viewModelScope.launch {
            loginRepository.isLoggedIn().collect {
                _state.value = LoginState(isLogin = it)
            }
        }
    }

    fun processIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.Logout -> logout()
            is LoginIntent.GoogleLogin -> login(intent.response)
            is LoginIntent.FailureLogin -> emitFailure(intent.messageError)
        }
    }

    private fun parseGoogleIdToken(
        response: GetCredentialResponse
    ): GoogleCredentialData? {

        val credential = response.credential as? CustomCredential ?: return null

        if (credential.type != GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            return null
        }

        val googleCredential =
            GoogleIdTokenCredential.createFrom(credential.data)

        return GoogleCredentialData(
            idToken = googleCredential.idToken,
            displayName = googleCredential.displayName,
            familyName = googleCredential.familyName,
            givenName = googleCredential.givenName,
            phoneNumber = googleCredential.phoneNumber,
            profileURL = googleCredential.profilePictureUri.toString()
        )
    }

    private fun login(response: GetCredentialResponse) {
        val googleCredentialData = parseGoogleIdToken(response)
        if (googleCredentialData != null) {
            viewModelScope.launch {
                Log.d("TAG", "$googleCredentialData")
                loginRepository.loginWithGoogle(googleCredentialData.idToken)
                _event.emit(LoginEvent.LoginSuccess)
            }
        } else {
            Log.e("TAG", "Invalid credential type")
            viewModelScope.launch {
                _event.emit(LoginEvent.LoginFailure("Invalid credential type"))
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            loginRepository.logout()
            _state.value = LoginState(isLogin = false)
        }
    }

    fun emitFailure(message: String) {
        viewModelScope.launch {
            _event.emit(LoginEvent.LoginFailure(message))
        }
    }
}
