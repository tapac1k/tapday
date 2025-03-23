package com.tapac1k.training.data.usecase

import com.tapac1k.training.contract.ExerciseGroup
import com.tapac1k.training.domain.TrainingService
import com.tapac1k.training.domain.usecase.SaveTrainingUseCase
import javax.inject.Inject

class SaveTrainingUseCaseImpl @Inject constructor(
    private val trainingService: TrainingService
) : SaveTrainingUseCase{
    override suspend fun invoke(trainingId: String?, exercises: List<ExerciseGroup>): Result<String> {
        return trainingService.saveTraining(trainingId, exercises)
    }
}