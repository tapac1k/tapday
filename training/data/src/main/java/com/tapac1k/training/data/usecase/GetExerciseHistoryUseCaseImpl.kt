package com.tapac1k.training.data.usecase

import android.util.Log
import com.tapac1k.training.contract.ExerciseGroup
import com.tapac1k.training.domain.TrainingService
import com.tapac1k.training.domain.usecase.GetExerciseHistoryUseCase
import javax.inject.Inject

class GetExerciseHistoryUseCaseImpl @Inject constructor(
    private val trainingService: TrainingService
) : GetExerciseHistoryUseCase {
    override suspend fun invoke(exerciseId: String, key: GetExerciseHistoryUseCase.HistoryKey?): Result<List<Pair<String, ExerciseGroup>>> {
        return trainingService.getSetsByExercise(exerciseId, key).onFailure {
            it.printStackTrace()
        }.onSuccess {
            Log.d("TestX", "Success")
        }
    }
}