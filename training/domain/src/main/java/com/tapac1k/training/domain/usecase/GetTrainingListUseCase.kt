package com.tapac1k.training.domain.usecase

import com.tapac1k.training.contract.ShortTrainingInfo

interface GetTrainingListUseCase {
    suspend fun invoke(date: Long? = null, id: String? = null): Result<List<ShortTrainingInfo>>
}