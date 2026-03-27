package com.king.evvn.presentation.login.ui

import android.content.Context
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.king.evvn.BuildConfig
import com.king.evvn.R
import com.king.evvn.presentation.login.viewmodel.LoginEvent
import com.king.evvn.presentation.login.viewmodel.LoginIntent
import com.king.evvn.presentation.login.viewmodel.LoginState
import com.king.evvn.presentation.login.viewmodel.LoginViewModel
import kotlinx.coroutines.launch
import java.security.SecureRandom

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    // Handle one-time events
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is LoginEvent.LoginSuccess -> onLoginSuccess()
                is LoginEvent.LoginFailure -> { /* Handle Error */ }
            }
        }
    }

    LoginContent(
        state = state,
        onIntent = viewModel::processIntent
    )
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
private fun LoginContent(
    state: LoginState,
    onIntent: (LoginIntent) -> Unit
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.fillMaxSize().padding(bottom = 50.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GoogleSignInButton(
                onClick = {
                    performGoogleSignIn(context) { intent ->
                        onIntent(intent)
                    }
                }
            )
        }
    }
}

@Composable
fun GoogleSignInButton(
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Icon(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = "Google Icon"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text("Sign in with Google")
    }
}

private fun generateSecureRandomNonce(byteLength: Int = 32): String {
    val randomBytes = ByteArray(byteLength)
    SecureRandom.getInstanceStrong().nextBytes(randomBytes)
    return Base64.encodeToString(randomBytes, Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP)
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
private fun performGoogleSignIn(
    context: Context,
    onResult: (LoginIntent) -> Unit
) {
    val credentialManager = CredentialManager.create(context)
    val webClientId = BuildConfig.WEB_CLIENT_ID
    
    val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(serverClientId = webClientId)
        .setNonce(generateSecureRandomNonce())
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(signInWithGoogleOption)
        .build()

    val coroutineScope = kotlinx.coroutines.MainScope()
    coroutineScope.launch {
        try {
            val result = credentialManager.getCredential(
                request = request,
                context = context,
            )
            onResult(LoginIntent.GoogleLogin(result))
        } catch (e: GetCredentialException) {
            onResult(LoginIntent.FailureLogin(e.message ?: "Unknown Error"))
        } catch (e: Exception) {
            onResult(LoginIntent.FailureLogin(e.message ?: "Unexpected Error"))
        }
    }
}
