package com.tapac1k.training.data.usecase

import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.domain.TrainingService
import com.tapac1k.training.domain.usecase.SaveExerciseUseCase
import javax.inject.Inject

class SaveExerciseUseCaseImpl @Inject constructor(
    private val service: TrainingService
) : SaveExerciseUseCase {
    override suspend fun invoke(exercise: Exercise): Result<Exercise> {
        return service.saveExercise(exercise)
    }


}