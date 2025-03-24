package com.tapac1k.domain.interactors.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Single usecase
 *
 * Based on suspend function
 */
abstract class SuspendUseCase<In, Out> {
    protected abstract suspend fun run(params: In): Out

    suspend operator fun invoke(params: In): Result<Out> {
        return try {
            val result = withContext(Dispatchers.IO) {
                run(params)
            }
            return Result.success(result)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}
