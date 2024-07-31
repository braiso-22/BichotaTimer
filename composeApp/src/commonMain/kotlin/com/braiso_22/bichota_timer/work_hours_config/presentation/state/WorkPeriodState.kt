package com.braiso_22.bichota_timer.work_hours_config.presentation.state

data class WorkPeriodState(
    val hoursPerDay: Float,
    val daysOfWeek: String,
    val from: String,
    val to: String,
    val daysOfPeriod: Int,
)
