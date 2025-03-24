package com.tapac1k.domain.interactors.tags

import com.tapac1k.domain.DatabaseRepository
import com.tapac1k.domain.interactors.base.SuspendUseCase
import javax.inject.Inject

class RenameTagInteractor @Inject constructor(
    val databaseRepository: DatabaseRepository
) : SuspendUseCase<RenameTagInteractor.Params, Unit>() {
    override suspend fun run(params: Params) {
        databaseRepository.renameTag(params.tag, params.newName)
    }

    data class Params(
        val tag: String,
        val newName: String
    )
}