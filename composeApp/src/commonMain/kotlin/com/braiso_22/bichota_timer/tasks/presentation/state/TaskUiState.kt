package com.braiso_22.bichota_timer.tasks.presentation.state

data class TaskUiState(
    val ticket: Int?,
    val title: String,
    val isWorkRelated: Boolean,
    val duration: String,
)
