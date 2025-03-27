package com.tapac1k.training.data.usecase

import com.tapac1k.training.contract.TrainingInfo
import com.tapac1k.training.domain.TrainingService
import com.tapac1k.training.domain.usecase.GetTrainingUseCase
import javax.inject.Inject

class GetTrainingUseCaseImpl @Inject constructor(
    private val trainingService: TrainingService
) : GetTrainingUseCase{
    override suspend fun invoke(id: String): Result<TrainingInfo> {
        return trainingService.getTraining(id)
    }
}