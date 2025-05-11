package com.tapac1k.day.domain.usecase

import com.tapac1k.day.domain.models.Habit
import kotlinx.coroutines.flow.Flow

fun interface SubscribeHabitsUseCase {
    suspend fun invoke(): Flow<List<Habit>>
}