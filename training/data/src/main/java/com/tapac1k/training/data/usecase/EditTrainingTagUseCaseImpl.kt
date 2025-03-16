package com.tapac1k.training.data.usecase

import com.tapac1k.training.domain.usecase.EditTrainingTagUseCase
import com.tapac1k.training.domain.TrainingService
import javax.inject.Inject

class EditTrainingTagUseCaseImpl @Inject constructor(
    private val service: TrainingService
) : EditTrainingTagUseCase {
    override suspend fun invoke(tagId: String, name: String): Result<Unit> {
        return service.editTrainingTag(tagId, name)
    }
}