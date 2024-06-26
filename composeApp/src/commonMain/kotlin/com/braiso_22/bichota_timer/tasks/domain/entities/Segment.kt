package com.braiso_22.bichota_timer.tasks.domain.entities

import com.raedghazal.kotlinx_datetime_ext.now
import kotlinx.datetime.LocalTime

data class Segment(
    val id: String = "",
    val start: LocalTime = LocalTime.now(),
    val end: LocalTime? = null,
    val executionId: String,
)
