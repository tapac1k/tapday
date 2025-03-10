package com.tapac1k.auth.data.usecase

import com.tapac1k.auth.domain.AuthService
import com.tapac1k.auth.domain.usecase.IsUserSignedInUseCase
import javax.inject.Inject

class IsUserSignedInUseCaseImpl @Inject constructor(
    private val authService: AuthService
):  IsUserSignedInUseCase{
    override fun invoke(): Boolean {
        return authService.isUserSignedIn()
    }
}