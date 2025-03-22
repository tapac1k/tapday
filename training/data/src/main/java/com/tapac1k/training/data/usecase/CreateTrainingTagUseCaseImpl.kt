package com.tapac1k.training.data.usecase

import com.tapac1k.training.contract.TrainingTag
import com.tapac1k.training.domain.usecase.CreateTrainingTagUseCase
import com.tapac1k.training.domain.TrainingService
import javax.inject.Inject

class CreateTrainingTagUseCaseImpl @Inject constructor(
    private val service: TrainingService
) : CreateTrainingTagUseCase {
    override suspend fun invoke(tagName: String): Result<TrainingTag> {
        return service.createTrainingTag(tagName)
    }
}