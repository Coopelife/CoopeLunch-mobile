package com.coopelife.croissant.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class KotlinNativeFlow<T>(source: Flow<T>) : Flow<T> by source {
    fun collect(onEach: (T) -> Unit, onCompletion: (cause: Throwable?) -> Unit): Cancellable {
        val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        scope.launch {
            try {
                collect {
                    onEach(it)
                }

                onCompletion(null)
            } catch (e: Throwable) {
                onCompletion(e)
            }
        }

        return object : Cancellable {
            override fun cancel() {
                scope.cancel()
            }
        }
    }
}

interface Cancellable {
    fun cancel()
}
