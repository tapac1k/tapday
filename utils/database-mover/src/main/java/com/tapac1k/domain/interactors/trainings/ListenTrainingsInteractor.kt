package com.tapac1k.domain.interactors.trainings

import com.tapac1k.domain.interactors.base.FlowUseCase
import com.tapac1k.domain.DatabaseRepository
import com.tapac1k.domain.entities.Training
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListenTrainingsInteractor @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : FlowUseCase<Unit, List<Training>>(){
    override suspend fun buildFlow(params: Unit): Flow<List<Training>> {
        return databaseRepository.listenTrainings()
    }
}