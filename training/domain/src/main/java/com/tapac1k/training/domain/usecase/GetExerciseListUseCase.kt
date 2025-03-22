package com.tapac1k.training.domain.usecase

import com.tapac1k.training.contract.Exercise
import kotlinx.coroutines.flow.Flow

interface GetExerciseListUseCase {
    suspend fun invoke(): Flow<List<Exercise>>
}