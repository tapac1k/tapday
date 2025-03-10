package com.tapac1k.auth.contract

import kotlinx.coroutines.flow.Flow

fun interface OnLogoutFlowUseCase {
    fun invoke(): Flow<Unit>
}