package com.king.evvn.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface AppRoute

@Serializable
data object Login : AppRoute

@Serializable
data object Home : AppRoute
