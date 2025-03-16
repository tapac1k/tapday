package com.tapac1k.training.data.usecase

import com.tapac1k.training.domain.usecase.GetTrainingTagsUseCase
import com.tapac1k.training.domain.TrainingService
import javax.inject.Inject

class GetTrainingTagsUseCaseImpl @Inject constructor(
    private val service: TrainingService
) : GetTrainingTagsUseCase {
    override suspend fun invoke() = service.getTrainingTags()

}