package com.braiso_22.bichota_timer.tasks.domain.entities

import com.raedghazal.kotlinx_datetime_ext.now
import kotlinx.datetime.LocalDate

data class Execution(
    val id: String = "",
    val date: LocalDate = LocalDate.now(),
    val segments: List<Segment> = emptyList(),
    val taskId: String,
)
