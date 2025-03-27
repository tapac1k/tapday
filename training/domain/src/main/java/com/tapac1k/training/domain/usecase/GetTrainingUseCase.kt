package com.tapac1k.training.domain.usecase

import com.tapac1k.training.contract.TrainingInfo

interface GetTrainingUseCase {
    suspend fun invoke(id: String) : Result<TrainingInfo>
}