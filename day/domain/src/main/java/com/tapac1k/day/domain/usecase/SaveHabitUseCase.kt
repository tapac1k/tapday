package com.tapac1k.day.domain.usecase

import com.tapac1k.day.domain.models.Habit

fun interface SaveHabitUseCase {
    suspend fun invoke(habit: Habit): Result<Unit>
}