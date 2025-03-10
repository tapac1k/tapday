package com.tapac1k.auth.data.usecase

import com.tapac1k.auth.domain.AuthService
import com.tapac1k.auth.domain.usecase.GoogleSignInUseCase
import javax.inject.Inject

class GoogleSignInUseCaseImpl  @Inject constructor(
    private val authService: AuthService
):  GoogleSignInUseCase{
    override suspend fun signIn() = authService.googleSignIn()
}