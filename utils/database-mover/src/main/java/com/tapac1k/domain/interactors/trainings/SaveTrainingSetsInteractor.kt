package com.tapac1k.domain.interactors.trainings

import com.tapac1k.domain.DatabaseRepository
import com.tapac1k.domain.entities.TrainSet
import com.tapac1k.domain.interactors.base.SuspendUseCase
import javax.inject.Inject

class SaveTrainingSetsInteractor @Inject constructor(
    private val databaseRepository: DatabaseRepository
): SuspendUseCase<SaveTrainingSetsInteractor.Params, Unit>() {
    override suspend fun run(params: Params) {
        databaseRepository.saveTrainingSets(params.trainingId, params.sets)
    }

    data class Params(
        val trainingId: Long,
        val sets: List<TrainSet>
    )
}