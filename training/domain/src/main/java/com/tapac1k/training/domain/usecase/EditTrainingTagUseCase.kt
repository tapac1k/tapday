package com.tapac1k.training.domain.usecase

fun interface EditTrainingTagUseCase {
    suspend fun invoke(tagId: String, name: String): Result<Unit>
}