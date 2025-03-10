package com.tapac1k.auth.domain.usecase

fun interface GoogleSignInUseCase {
    suspend fun signIn(): Result<Unit>
}