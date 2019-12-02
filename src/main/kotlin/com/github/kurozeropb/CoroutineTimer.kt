package com.github.kurozeropb

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield

object CoroutineTimer {

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

    fun timeout(delayMillis: Long = 0, action: suspend TimerScope.() -> Unit) = GlobalScope.launch {
        val scope = TimerScope()
        delay(delayMillis)
        if (!scope.isCanceled) {
            action(scope)
        }
        yield()
    }

}

class TimerScope {
    var isCanceled: Boolean = false
        private set

    fun cancel() {
        isCanceled = true
    }
}