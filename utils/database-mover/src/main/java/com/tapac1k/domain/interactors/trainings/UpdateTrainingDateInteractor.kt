package com.tapac1k.domain.interactors.trainings

import com.tapac1k.domain.interactors.base.SuspendUseCase
import com.tapac1k.domain.DatabaseRepository
import javax.inject.Inject

class UpdateTrainingDateInteractor @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : SuspendUseCase<UpdateTrainingDateInteractor.Params, Unit>() {
    override suspend fun run(params: Params) {
        return databaseRepository.updateTrainingDate(params.trainingId, params.newDate)
    }

    class Params(
        val trainingId: Long,
        val newDate: Long
    )
}