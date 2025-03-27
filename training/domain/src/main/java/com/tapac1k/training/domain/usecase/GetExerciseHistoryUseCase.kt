package com.tapac1k.training.domain.usecase

import com.tapac1k.training.contract.ExerciseGroup

fun interface GetExerciseHistoryUseCase {
   suspend fun invoke(exerciseId: String, key: HistoryKey?): Result<List<Pair<String, ExerciseGroup>>>

   data class HistoryKey(
      val afterTrainingId: String,
      val afterId: String,
      val date: Long?
   )
}
