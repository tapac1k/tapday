package com.tapac1k.training.data.usecase

import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.domain.TrainingService
import com.tapac1k.training.domain.usecase.GetExerciseListUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExerciseListUseCaseImpl @Inject constructor(
    private val service: TrainingService
) : GetExerciseListUseCase {
    override suspend fun invoke(): Flow<List<Exercise>> {
        return service.getExercises()
    }
}