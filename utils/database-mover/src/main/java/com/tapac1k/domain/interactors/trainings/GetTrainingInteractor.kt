package com.tapac1k.domain.interactors.trainings

import com.tapac1k.domain.DatabaseRepository
import com.tapac1k.domain.entities.Training
import com.tapac1k.domain.interactors.base.SuspendUseCase
import javax.inject.Inject

class GetTrainingInteractor @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : SuspendUseCase<Long, Training>() {
    override suspend fun run(params: Long): Training {
        return databaseRepository.getTraining(params)
    }
}