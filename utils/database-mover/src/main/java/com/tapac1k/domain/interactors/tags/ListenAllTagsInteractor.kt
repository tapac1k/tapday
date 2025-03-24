package com.tapac1k.domain.interactors.tags

import com.tapac1k.domain.DatabaseRepository
import com.tapac1k.domain.interactors.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListenAllTagsInteractor @Inject constructor(
    private val databaseRepository: DatabaseRepository
): FlowUseCase<Unit, List<String>>() {
    override suspend fun buildFlow(params: Unit): Flow<List<String>> {
        return databaseRepository.listenTags()
    }
}