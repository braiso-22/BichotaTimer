package com.braiso_22.bichota_timer.tasks.presentation.state

data class MyDayUiState(
    val stats: DayStatsUiState = DayStatsUiState(),
    val inProgressTasks: List<TaskUiState> = emptyList(),
    val pendingTasks: List<TaskUiState> = emptyList(),
    val completedTasks: List<TaskUiState> = emptyList(),
)
