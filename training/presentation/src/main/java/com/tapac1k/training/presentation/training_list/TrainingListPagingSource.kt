package com.tapac1k.training.presentation.training_list

import androidx.paging.PagingSource
import androidx.paging.PagingState

class TrainingListPagingSource(
    private val getTrainingListPagingSource: TrainingListPagingSource
): PagingSource<Long, DayInfo>() {

    override suspend fun load(
        params: LoadParams<Long>
    ): LoadResult<Long, DayInfo> {
        try {
            val nextPageNumber = params.key ?: currentDay
            val response = getDayListByRangeUseCase.invoke(nextPageNumber, nextPageNumber - params.loadSize + 1)
            val next = nextPageNumber - params.loadSize
            val prev = if (nextPageNumber == currentDay) null else (nextPageNumber + params.loadSize).coerceAtMost(currentDay)
            return LoadResult.Page(
                data = response.getOrThrow(),
                prevKey = prev,
                nextKey = next
            )
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getRefreshKey(state: PagingState<Long, DayInfo>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.data?.first()?.id
        }
    }
}