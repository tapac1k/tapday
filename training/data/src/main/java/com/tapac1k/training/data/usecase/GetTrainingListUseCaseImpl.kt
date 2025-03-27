package com.tapac1k.training.data.usecase

import com.tapac1k.training.contract.ShortTrainingInfo
import com.tapac1k.training.domain.TrainingService
import com.tapac1k.training.domain.usecase.GetTrainingListUseCase
import javax.inject.Inject

class GetTrainingListUseCaseImpl @Inject constructor(
    private val trainingService: TrainingService
) : GetTrainingListUseCase{

    override suspend fun invoke(date: Long?, id: String?): Result<List<ShortTrainingInfo>> {
        return trainingService.getTrainings(date, id).onFailure {
            it.printStackTrace()
        }
    }
}