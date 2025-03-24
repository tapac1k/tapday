package com.tapac1k.domain.interactors.tags

import com.tapac1k.domain.DatabaseRepository
import com.tapac1k.domain.interactors.base.SuspendUseCase
import javax.inject.Inject

class CreateTagInteractor @Inject constructor(
    val databaseRepository: DatabaseRepository
) : SuspendUseCase<String, Unit>() {
    override suspend fun run(params: String) {
        databaseRepository.addTag(params)
    }
}