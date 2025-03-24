package com.tapac1k.domain.interactors.exercise

import com.tapac1k.domain.DatabaseRepository
import com.tapac1k.domain.entities.Exercise
import com.tapac1k.domain.entities.Training
import com.tapac1k.domain.interactors.base.SuspendUseCase
import javax.inject.Inject

class GetExerciseInteractor @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : SuspendUseCase<Long, Exercise>() {
    override suspend fun run(params: Long): Exercise {
        return databaseRepository.getExercise(params)
    }
}