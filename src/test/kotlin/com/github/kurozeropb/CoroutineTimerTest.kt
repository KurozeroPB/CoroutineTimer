package com.github.kurozeropb

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CoroutineTimerTest {

    @Test
    fun interval() {
        var i = 0
        CoroutineTimer.interval(0, 2000) {
            i++
        }

        Thread.sleep(6000)

        assertEquals(3, i)
    }

    @Test
    fun timeout() {
        var test: String? = null
        CoroutineTimer.timeout(5000) {
            test = "5000"
        }

        Thread.sleep(6000) // Wait for timeout to finish

        assertEquals("5000", test)
    }

}