package com.king.evvn.presentation.navigation
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.king.evvn.presentation.home.ui.HomeScreen
import com.king.evvn.presentation.login.ui.LoginScreen
import com.king.evvn.presentation.login.viewmodel.LoginViewModel


sealed interface HomeTab {
    data object Home : HomeTab
    data object Favorite : HomeTab
    data object Map : HomeTab
    data object News : HomeTab
    data object More : HomeTab
}

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun AppNavigation(
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val backStack = remember { mutableStateListOf<AppRoute>(Login) }
    val currentTab = remember { mutableStateOf<HomeTab>(HomeTab.Home) }
    val state by loginViewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isLogin) {
        if (state.isLogin) {
            backStack.clear()
            backStack.add(Home)
        }
    }

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) backStack.removeLastOrNull()
        },
        entryProvider = { key ->
            when (key) {
                is Login -> NavEntry(key) {
                    LoginScreen(
                        viewModel = loginViewModel,
                        onLoginSuccess = {
                            backStack.clear()
                            backStack.add(Home)
                        }
                    )
                }

                is Home -> NavEntry(key) {
                    HomeScreen(
                        currentTab = currentTab.value,
                        onTabSelected = { newTab ->
                            currentTab.value = newTab
                        }
                    )
                }
            }
        }
    )
}
