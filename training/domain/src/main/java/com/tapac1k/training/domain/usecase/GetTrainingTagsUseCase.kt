package com.tapac1k.training.domain.usecase

import com.tapac1k.training.contract.TrainingTag
import kotlinx.coroutines.flow.Flow

interface GetTrainingTagsUseCase {
    suspend fun invoke(): Flow<List<TrainingTag>>
}