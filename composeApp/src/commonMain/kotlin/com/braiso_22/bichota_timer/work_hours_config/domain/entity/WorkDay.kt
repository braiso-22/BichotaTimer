package com.braiso_22.bichota_timer.work_hours_config.domain.entity

import kotlinx.datetime.LocalDate

data class WorkDay(
    val date: LocalDate,
    val hours: Float,
)

fun List<WorkDay>.sumOfHours() = this.sumOf {
    it.hours.toDouble()
}.toFloat()