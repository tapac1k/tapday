package com.tapac1k.domain.interactors.exercise

import com.tapac1k.domain.DatabaseRepository
import com.tapac1k.domain.entities.Training
import com.tapac1k.domain.interactors.base.SuspendUseCase
import javax.inject.Inject

class GetExerciseHistoryInteractor @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : SuspendUseCase<Long, List<Training>>() {
    override suspend fun run(params: Long): List<Training> {
        return databaseRepository.getTrainingsWithExercise(params)
    }
}