package com.tapac1k.auth.data.usecase

import com.tapac1k.auth.contract.OnLogoutFlowUseCase
import com.tapac1k.auth.domain.AuthService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnLogoutFlowUseCaseImpl @Inject constructor(
    private val authService: AuthService
) : OnLogoutFlowUseCase {
    override fun invoke(): Flow<Unit> {
        return authService.subscribeUserLoggedOut()
    }
}