package com.tapac1k.training.data.usecase

import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.domain.TrainingService
import com.tapac1k.training.domain.usecase.GetExerciseDetailsUseCase
import javax.inject.Inject

class GetExerciseDetailsUseCaseImpl @Inject constructor(
    private val service: TrainingService
) : GetExerciseDetailsUseCase {
    override suspend fun invoke(exerciseId: String): Result<Exercise> {
        return service.getExerciseDetails(exerciseId)
    }
}