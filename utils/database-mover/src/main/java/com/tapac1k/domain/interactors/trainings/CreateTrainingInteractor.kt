package com.tapac1k.domain.interactors.trainings

import com.tapac1k.domain.interactors.base.SuspendUseCase
import com.tapac1k.domain.DatabaseRepository
import javax.inject.Inject

class CreateTrainingInteractor @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : SuspendUseCase<Unit, Long>() {
    override suspend fun run(params: Unit): Long {
        return databaseRepository.createTraining()
    }
}