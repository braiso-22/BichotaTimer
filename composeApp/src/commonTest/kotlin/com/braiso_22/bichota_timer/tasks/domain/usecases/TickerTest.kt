package com.braiso_22.bichota_timer.tasks.domain.usecases


import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds


class TickerTest {

    private lateinit var ticker: Ticker

    @BeforeTest
    fun setup() {
        ticker = Ticker(100.milliseconds)
    }

    @Test
    fun `ticker should not emit when not started`(): Unit = runBlocking {
        assertFails {
            withTimeout(500.milliseconds) {
                ticker.tick().first()
            }
        }
    }

    @Test
    fun `ticker should emit when started`() = runBlocking {
        ticker.start()
        val emissions = withTimeout(500.milliseconds) {
            ticker.tick().take(3).toList()
        }
        assertEquals(3, emissions.size)
        ticker.stop()
    }

    @Test
    fun `ticker should stop emitting when stopped`() = runBlocking {
        ticker.start()
        val emissions = mutableListOf<Unit>()
        val job = launch {
            ticker.tick().collect { emissions.add(Unit) }
        }

        delay(350.milliseconds) // Should collect about 3 emissions
        ticker.stop()
        delay(350.milliseconds) // Should not collect any more emissions

        job.cancel()
        assertTrue(emissions.size in 3..4) // Allow for some timing flexibility
    }
}