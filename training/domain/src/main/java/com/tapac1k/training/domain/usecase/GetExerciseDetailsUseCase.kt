package com.tapac1k.training.domain.usecase

import com.tapac1k.training.contract.Exercise

fun interface GetExerciseDetailsUseCase {
    suspend fun invoke(exerciseId: String): Result<Exercise>
}