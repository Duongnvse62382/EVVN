package com.king.evvn

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.king.evvn.presentation.navigation.AppNavigation
import com.king.evvn.presentation.splash.viewmodel.SplashIntent
import com.king.evvn.presentation.splash.viewmodel.SplashViewModel
import com.king.evvn.ui.theme.EVVNTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        
        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()
            
            LaunchedEffect(Unit) {
                viewModel.processIntent(SplashIntent.Initialize)
            }
            
            splashScreen.setKeepOnScreenCondition {
                state.isLoading
            }

            EVVNTheme {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    AppNavigation()
                }
            }
        }
    }
}
