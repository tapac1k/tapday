package com.tapac1k.training.presentation.training_list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tapac1k.training.contract.ShortTrainingInfo
import com.tapac1k.training.domain.usecase.GetTrainingListUseCase

class TrainingListPagingSource(
    private val getTrainingListUseCase: GetTrainingListUseCase
): PagingSource<Pair<Long, String>, ShortTrainingInfo>() {

    override suspend fun load(
        params: LoadParams<Pair<Long, String>>
    ): LoadResult<Pair<Long, String>, ShortTrainingInfo> {
        try {
            val nextPageNumber = params.key
            val response = getTrainingListUseCase.invoke(nextPageNumber?.first, nextPageNumber?.second)
            val next = response.getOrNull()?.lastOrNull()?.let { it.date to it.id }
            return LoadResult.Page(
                data = response.getOrThrow(),
                prevKey = null,
                nextKey = next
            )
        } catch (e: Exception) {
            throw e
        }
    }

    override fun getRefreshKey(state: PagingState<Pair<Long, String>, ShortTrainingInfo>): Pair<Long, String>? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.data?.first()?.let { it.date to it.id }
        }
    }
}