package com.tapac1k.day.data.usecase

import com.tapac1k.day.domain.models.Habit
import com.tapac1k.day.domain.service.DayService
import com.tapac1k.day.domain.usecase.SubscribeHabitsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscribeHabitsUseCaseImpl @Inject constructor(
    private val dayService: DayService
) : SubscribeHabitsUseCase {
    override suspend fun invoke(): Flow<List<Habit>> {
        return dayService.subscribeAllHabits()
    }
}