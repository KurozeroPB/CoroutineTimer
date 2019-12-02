package com.github.kurozeropb

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

/**
 * @since 1.0.0
 */
object CoroutineTimer {

    /**
     * @since 1.0.0
     *
     * @param delayMillis The initial delay in milliseconds
     * @param intervalMillis The interval in milliseconds
     * @param action The action to execute every x milliseconds
     */
    fun interval(delayMillis: Long = 0, intervalMillis: Long = 1000, action: suspend TimerScope.() -> Unit) = GlobalScope.launch {
        val scope = TimerScope()
        delay(delayMillis) // Initial delay

        while (true) {
            if (scope.isCanceled) {
                break
            }

            action(scope)
            delay(intervalMillis) // Delay after every execution

            yield()
        }
    }

    /**
     * @since 1.0.0
     *
     * @param delayMillis The timeout delay in milliseconds
     * @param action The action to execute after the delay
     */
    fun timeout(delayMillis: Long = 0, action: suspend TimerScope.() -> Unit) = GlobalScope.launch {
        val scope = TimerScope()
        delay(delayMillis)
        if (!scope.isCanceled) {
            action(scope)
        }
    }

}

/**
 * @since 1.0.0
 *
 * The timer action scope
 */
class TimerScope {
    var isCanceled: Boolean = false
        private set

    /**
     * @since 1.0.0
     *
     * Cancel the currently running interval or timeout
     */
    fun cancel() {
        isCanceled = true
    }
}