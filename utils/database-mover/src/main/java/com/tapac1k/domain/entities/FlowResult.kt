package com.tapac1k.domain.entities

class FlowResult<out T> internal constructor(
    internal val value: Any?
) {
    val isSuccess: Boolean get() = value !is Failure

    val isFailure: Boolean get() = value is Failure

    internal class Failure(
        val exception: Throwable
    ) {
        override fun equals(other: Any?): Boolean = other is Failure && exception == other.exception
        override fun hashCode(): Int = exception.hashCode()
        override fun toString(): String = "Failure($exception)"
    }

    companion object {
        fun <T> success(value: T): FlowResult<T> =
            FlowResult(value)

        fun <T> failure(exception: Throwable): FlowResult<T> =
            FlowResult(Failure(exception))
    }

    suspend fun onSuccess(action: suspend (value: T) -> Unit): FlowResult<T> {
        if (isSuccess) action(value as T)
        return this
    }

    suspend fun onFailure(action: suspend (value: Throwable?) -> Unit): FlowResult<T> {
        exceptionOrNull()?.let { action }
        return this
    }

    fun exceptionOrNull(): Throwable? =
        when (value) {
            is FlowResult.Failure -> value.exception
            else -> null
        }

    fun getOrNull(): T? =
        when {
            isFailure -> null
            else -> value as T
        }
}