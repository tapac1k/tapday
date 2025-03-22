package com.tapac1k.training.domain.usecase

import com.tapac1k.training.contract.TrainingTag

fun interface CreateTrainingTagUseCase {
    suspend fun invoke(tagName: String): Result<TrainingTag>
}