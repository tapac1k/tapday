package com.tapac1k.utils.common

import kotlinx.coroutines.CancellationException

suspend inline fun <R>resultOf(block: () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(e)
    }
}