package com.braiso_22.bichota_timer.work_hours_config.domain.entity

import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

data class WorkHoursConfig(
    val id: String,
    val daysOfWeek: List<DayOfWeek>,
    val hoursPerDay: Float,
    val from: LocalDate,
    val to: LocalDate,
    val userId: String,
)
