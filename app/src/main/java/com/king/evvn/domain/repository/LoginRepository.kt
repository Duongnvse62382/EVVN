package com.king.evvn.domain.repository

import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun loginWithGoogle(idToken: String)
    fun isLoggedIn(): Flow<Boolean>
    suspend fun logout()
}