package com.tapac1k.training.presentation.exercise_history

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tapac1k.training.contract.ExerciseGroup
import com.tapac1k.training.domain.usecase.GetExerciseHistoryUseCase

class ExerciseHistoryPagingSource(
    private val exerciseId: String,
    private val getExerciseHistoryUseCase: GetExerciseHistoryUseCase
) : PagingSource<GetExerciseHistoryUseCase.HistoryKey, Pair<String, ExerciseGroup>>() {

    override suspend fun load(
        params: LoadParams<GetExerciseHistoryUseCase.HistoryKey>
    ): LoadResult<GetExerciseHistoryUseCase.HistoryKey, Pair<String, ExerciseGroup>> {
        try {
            val nextPageNumber = params.key
            val response = getExerciseHistoryUseCase.invoke(exerciseId, nextPageNumber)
            val next = response.getOrNull()?.lastOrNull()?.let {
                GetExerciseHistoryUseCase.HistoryKey(
                    it.first,
                    it.second.id,
                    it.second.date
                )
            }
            return LoadResult.Page(
                data = response.getOrThrow(),
                prevKey = null,
                nextKey = next
            )
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getRefreshKey(state: PagingState<GetExerciseHistoryUseCase.HistoryKey, Pair<String, ExerciseGroup>>): GetExerciseHistoryUseCase.HistoryKey? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.data?.first()?.let {
                GetExerciseHistoryUseCase.HistoryKey(
                    it.first,
                    it.second.id,
                    it.second.date
                )
            }
        }
    }
}