package com.braiso_22.bichota_timer.tasks.presentation.my_day.state

data class TaskUiState(
    val id: String,
    val ticket: Int?,
    val title: String,
    val isWorkRelated: Boolean,
    val duration: String,
)
