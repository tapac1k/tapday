package com.tapac1k.training.domain.usecase

import com.tapac1k.training.contract.Exercise

fun interface SaveExerciseUseCase {
    suspend fun invoke(exercise: Exercise): Result<Exercise>
}