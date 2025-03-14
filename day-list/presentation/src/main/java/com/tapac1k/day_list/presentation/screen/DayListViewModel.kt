package com.tapac1k.day_list.presentation.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.tapac1k.day.contract.DayInfo
import com.tapac1k.day.contract.GetCurrentDayIdUseCase
import com.tapac1k.day_list.domain.GetDayListByRangeUseCase
import com.tapac1k.day_list.presentation.DayListPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DayListViewModel @Inject constructor(
    private val getCurrentDayIdUseCase: GetCurrentDayIdUseCase,
    private val getDayListByRangeUseCase: GetDayListByRangeUseCase,
) : ViewModel() {
    private val day = getCurrentDayIdUseCase.invoke()
    val dayFlow: Flow<PagingData<DayInfo>> = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 20)
    ) {
        DayListPagingSource(day, getDayListByRangeUseCase)
    }.flow
        .cachedIn(viewModelScope)

    fun getCurrentDay() = getCurrentDayIdUseCase.invoke()
}