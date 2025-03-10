package com.tapac1k.auth.domain

import kotlinx.coroutines.flow.Flow

interface AuthService {
    fun init()
    fun subscribeUserLoggedOut(): Flow<Unit>
    fun logout()
    fun isUserSignedIn(): Boolean
    suspend fun googleSignIn(): Result<Unit>
}