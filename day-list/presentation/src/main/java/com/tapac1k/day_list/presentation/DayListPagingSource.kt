package com.tapac1k.day_list.presentation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tapac1k.day.contract.DayInfo
import com.tapac1k.day_list.domain.GetDayListByRangeUseCase

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
            val prev = nextPageNumber + params.loadSize
            return LoadResult.Page(
                data = response.getOrThrow(),
                prevKey = prev.takeIf { it <= currentDay },
                nextKey = next
            )
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getRefreshKey(state: PagingState<Long, DayInfo>): Long? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}