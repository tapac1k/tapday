package com.tapac1k.training.domain.usecase

fun interface CreateTrainingTagUseCase {
    suspend fun invoke(tagName: String): Result<Unit>
}