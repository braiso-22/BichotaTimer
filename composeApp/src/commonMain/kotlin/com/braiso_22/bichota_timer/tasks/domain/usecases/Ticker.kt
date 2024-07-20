package com.braiso_22.bichota_timer.tasks.domain.usecases

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class Ticker(private val period: Duration = 1.seconds) {
    private val _tickFlow = MutableSharedFlow<Unit>()
    private var job: Job? = null

    fun tick(): Flow<Unit> = _tickFlow.asSharedFlow()

    fun start() {
        if (job == null) {
            job = CoroutineScope(Dispatchers.Default).launch {
                while (isActive) {
                    _tickFlow.emit(Unit)
                    delay(period)
                }
            }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }
}