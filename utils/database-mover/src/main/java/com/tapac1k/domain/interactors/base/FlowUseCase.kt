package com.tapac1k.domain.interactors.base

import com.tapac1k.domain.entities.FlowResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlin.coroutines.coroutineContext

/**
 *  Observable usecase
 *
 * Based on Flows: https://kotlinlang.org/docs/reference/coroutines/flow.html
 */
abstract class FlowUseCase<In, Out> {
    protected abstract suspend fun buildFlow(params: In): Flow<Out>

    suspend operator fun invoke(params: In, onData: (suspend FlowResult<Out>.() -> Unit)? = null) {
        try {
            with(CoroutineScope(coroutineContext)) {
                buildFlow(params)
                    .map {
                        it.asResult()
                    }
                    .catch { FlowResult.failure<Out>(it) }
                    .flowOn(Dispatchers.IO)
                    .onEach { onData?.invoke(it) }
                    .flowOn(Dispatchers.Main)
                    .launchIn(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            onData?.invoke(FlowResult.failure(e))
        }
    }

    fun Out.asResult() = FlowResult.success(this)
}
