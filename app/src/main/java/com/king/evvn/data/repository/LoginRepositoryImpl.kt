package com.king.evvn.data.repository

import com.king.evvn.data.source.local.SessionManager
import com.king.evvn.domain.repository.LoginRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class LoginRepositoryImpl @Inject constructor(private val sessionManager: SessionManager) : LoginRepository {
    override suspend fun loginWithGoogle(idToken: String) {
        sessionManager.saveSession(idToken)
    }

    override fun isLoggedIn(): Flow<Boolean> {
        return sessionManager.isLoggedIn()
    }

    override suspend fun logout(){
        return sessionManager.clearSession()
    }
}