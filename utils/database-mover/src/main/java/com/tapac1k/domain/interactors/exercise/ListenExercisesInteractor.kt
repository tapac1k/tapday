package com.tapac1k.domain.interactors.exercise

import com.tapac1k.domain.DatabaseRepository
import com.tapac1k.domain.entities.Exercise
import com.tapac1k.domain.entities.Training
import com.tapac1k.domain.interactors.base.FlowUseCase
import com.tapac1k.domain.interactors.base.SuspendUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListenExercisesInteractor @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : FlowUseCase<Unit, List<Exercise>>() {
    override suspend fun buildFlow(params: Unit): Flow<List<Exercise>> {
        return databaseRepository.listenExercises()
    }


}