package com.tapac1k.training.data.usecase

import com.tapac1k.training.contract.TrainingInfo
import com.tapac1k.training.domain.TrainingService
import com.tapac1k.training.domain.usecase.GetTrainingsUseCase
import javax.inject.Inject

class GetTrainingUseCaseImpl @Inject constructor(
    private val trainingService: TrainingService
) : GetTrainingsUseCase{

    override suspend fun invoke(date: Long): Result<List<TrainingInfo>> {
        return trainingService.getTrainings(date)
    }
}