package com.tapac1k.day.presentation.daylist

import androidx.collection.intFloatMapOf
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tapac1k.day.contract.DayInfo
import com.tapac1k.day.domain.usecase.GetDayListByRangeUseCase

class DayListPagingSource(
    private val currentDay: Long,
    private val getDayListByRangeUseCase: GetDayListByRangeUseCase
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