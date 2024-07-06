package com.braiso_22.bichota_timer.tasks.presentation.my_day.state

data class DayStatsUiState(
    val hoursWorked: Float = 0f,
    val hoursToWork: Float = 0f,
    val timeFrom: String = "",
    val timeTo: String = "",
)
