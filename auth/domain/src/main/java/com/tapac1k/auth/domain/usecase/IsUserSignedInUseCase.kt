package com.tapac1k.auth.domain.usecase

fun interface IsUserSignedInUseCase {
    fun invoke(): Boolean
}