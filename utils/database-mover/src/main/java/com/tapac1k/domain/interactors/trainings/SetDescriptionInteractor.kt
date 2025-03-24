package com.tapac1k.domain.interactors.trainings

import com.tapac1k.domain.interactors.base.SuspendUseCase
import com.tapac1k.domain.DatabaseRepository
import javax.inject.Inject

class SetDescriptionInteractor @Inject constructor(
    private val databaseRepository: DatabaseRepository
) : SuspendUseCase<SetDescriptionInteractor.Params, Unit>() {
    override suspend fun run(params: Params) {
        return databaseRepository.setDescription(params.id, params.desc)
    }

    data class Params(
        val id: Long,
        val desc: String?
    )
}