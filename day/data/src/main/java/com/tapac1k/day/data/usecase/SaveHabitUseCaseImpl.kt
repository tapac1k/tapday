package com.tapac1k.day.data.usecase

import com.tapac1k.day.domain.models.Habit
import com.tapac1k.day.domain.service.DayService
import com.tapac1k.day.domain.usecase.SaveHabitUseCase
import javax.inject.Inject

class SaveHabitUseCaseImpl @Inject constructor(
    private val dayService: DayService,
) : SaveHabitUseCase {
    override suspend fun invoke(habit: Habit): Result<Unit> {
        return dayService.saveHabit(habit).map {  }
    }
}