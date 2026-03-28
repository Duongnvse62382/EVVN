package com.king.evvn.presentation.login.ui

import android.content.Context
import android.os.Build
import android.util.Base64
import android.widget.Toast
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.king.evvn.BuildConfig
import com.king.evvn.R
import com.king.evvn.presentation.login.viewmodel.LoginEvent
import com.king.evvn.presentation.login.viewmodel.LoginIntent
import com.king.evvn.presentation.login.viewmodel.LoginViewModel
import com.king.evvn.ui.theme.EVVNTheme // Assuming this is your project's theme
import kotlinx.coroutines.launch
import java.security.SecureRandom

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit
) {
    // Handle one-time events
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is LoginEvent.LoginSuccess -> onLoginSuccess()
                is LoginEvent.LoginFailure -> {
                    Toast.makeText(context, "Login Failed: ${event.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    LoginContent(
        onIntent = viewModel::processIntent
    )
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
private fun LoginContent(
    onIntent: (LoginIntent) -> Unit
) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Ensure this drawable exists
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
                    // Note: performGoogleSignIn uses CredentialManager and LocalContext,
                    // which cannot be fully previewed in a design-time environment.
                    // The preview will show the UI elements but won't execute the sign-in flow.
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
        Icon( tint = null,
            painter = painterResource(id = R.drawable.ic_google), // Ensure this drawable exists
            contentDescription = "Google Icon"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = stringResource(id = R.string.sign_in_google)) // Ensure this string resource exists
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
    val webClientId = BuildConfig.WEB_CLIENT_ID // Ensure BuildConfig is available

    val signInWithGoogleOption = GetSignInWithGoogleOption.Builder(serverClientId = webClientId)
        .setNonce(generateSecureRandomNonce())
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(signInWithGoogleOption)
        .build()

    val coroutineScope = kotlinx.coroutines.MainScope() // Be mindful of MainScope in production code if not managed correctly
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

// Preview composable for LoginContent
@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Preview(showBackground = true)
@Composable
fun PreviewLoginContent() {
    EVVNTheme { // Apply the project's theme
        LoginContent(
            onIntent = { intent ->
                // This lambda will be called when the button is clicked in the preview
                // For preview purposes, we can just log or do nothing.
                println("Login Intent received in preview: $intent")
            }
        )
    }
}

