package com.tapac1k.domain.interactors.exercise

import com.tapac1k.domain.DatabaseRepository
import com.tapac1k.domain.entities.Exercise
import com.tapac1k.domain.interactors.base.SuspendUseCase
import javax.inject.Inject

class SaveExerciseInteractor @Inject constructor(
    private val databaseRepository: DatabaseRepository
): SuspendUseCase<Exercise, Unit>() {
    override suspend fun run(params: Exercise) {
        databaseRepository.saveExercise(params)
    }
}