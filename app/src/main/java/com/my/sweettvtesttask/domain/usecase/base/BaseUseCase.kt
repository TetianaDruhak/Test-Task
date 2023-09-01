package com.my.sweettvtesttask.domain.usecase.base

import com.my.sweettvtesttask.data.api.NoConnectionInterceptor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext


typealias CompletionBlock<T> = BaseUseCase.Request<T>.() -> Unit

abstract class BaseUseCase<V, T> {

    private var parentJob: Job = Job()
    private var backgroundContext: CoroutineContext = Dispatchers.IO
    private var foregroundContext: CoroutineContext = Dispatchers.Main

    protected abstract suspend fun executeOnBackground(data: V): T

    fun execute(data: V, block: CompletionBlock<T>) {
        execute(data = data, delayBeforeJob = 0L, block = block)
    }

    fun execute(data: V, delayBeforeJob: Long = 0L, block: CompletionBlock<T>) {
        val response = Request<T>().apply { block() }
        unsubscribe()
        parentJob = Job()
        CoroutineScope(foregroundContext + parentJob).launch {
            delay(delayBeforeJob)
            try {
                val result = withContext(backgroundContext) {
                    executeOnBackground(data)
                }
                response(result)
            } catch (cancellationException: NoConnectionInterceptor.NoInternetException) {
                response(cancellationException)
            } catch (e: Exception) {
                response(e)
            }
        }
    }

    private fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }

    class Request<T> {
        private var onComplete: ((T) -> Unit)? = null
        private var onError: ((Exception) -> Unit)? = null
        private var onNoInternet: ((NoConnectionInterceptor.NoInternetException) -> Unit)? = null

        fun onComplete(block: (T) -> Unit) {
            onComplete = block
        }

        fun onError(block: (Exception) -> Unit) {
            onError = block
        }

        fun onNoInternet(block: (NoConnectionInterceptor.NoInternetException) -> Unit) {
            onNoInternet = block
        }

        operator fun invoke(result: T) {
            onComplete?.invoke(result)
        }

        operator fun invoke(error: Exception) {
            onError?.invoke(error)
        }

        operator fun invoke(error: NoConnectionInterceptor.NoInternetException) {
            onNoInternet?.invoke(error)
        }
    }
}