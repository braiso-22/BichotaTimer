package com.braiso_22.bichota_timer.tasks.domain.entities

import com.raedghazal.kotlinx_datetime_ext.now
import kotlinx.datetime.LocalDateTime

data class Segment(
    val id: String = "",
    val start: LocalDateTime = LocalDateTime.now(),
    val end: LocalDateTime? = null,
    val executionId: String,
)
