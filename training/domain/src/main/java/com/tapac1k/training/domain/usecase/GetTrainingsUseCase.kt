package com.tapac1k.training.domain.usecase

import com.tapac1k.training.contract.TrainingInfo

interface GetTrainingsUseCase {
    suspend fun invoke(date: Long? = null): Result<List<TrainingInfo>>
}