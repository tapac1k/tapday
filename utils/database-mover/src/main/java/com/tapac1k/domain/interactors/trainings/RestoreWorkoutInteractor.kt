package com.tapac1k.domain.interactors.trainings

import com.tapac1k.domain.interactors.base.SuspendUseCase
import com.tapac1k.domain.DatabaseRepository
import javax.inject.Inject

class RestoreWorkoutInteractor @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : SuspendUseCase<Long, Unit>() {
    override suspend fun run(params: Long) {
        return databaseRepository.undeleteTraining(params)
    }

}