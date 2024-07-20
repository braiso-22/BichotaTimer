package com.braiso_22.bichota_timer.tasks.domain.entities

data class CategorizedTasks(
    val completed: List<Task>,
    val running: List<Task>,
    val pending: List<Task>,
)
