package com.tapac1k.training.domain.usecase

import com.tapac1k.training.contract.ExerciseGroup

interface SaveTrainingUseCase {
    suspend fun invoke(trainingId: String?, exercises: List<ExerciseGroup>): Result<String>
}