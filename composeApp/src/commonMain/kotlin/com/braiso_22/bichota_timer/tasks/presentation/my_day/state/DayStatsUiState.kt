package com.braiso_22.bichota_timer.tasks.presentation.my_day.state

data class DayStatsUiState(
    val progress: ProgressBarUiState = ProgressBarUiState(),
    val timeFrom: String = "",
    val timeTo: String = "",
)
