package com.tapac1k.auth.data.usecase

import com.tapac1k.auth.contract.LogoutUseCase
import com.tapac1k.auth.domain.AuthService
import javax.inject.Inject

class LogoutUseCaseImpl @Inject constructor(
    private val authService: AuthService
): LogoutUseCase {
    override fun invoke() {
        authService.logout()
    }
}