package com.braiso_22.bichota_timer.tasks.presentation.add_task

import com.braiso_22.bichota_timer.tasks.domain.entities.Task

data class AddTaskUiState(
    val id: String = "",
    val name: String = "",
    val isWorkRelated: Boolean = true,
    val ticketId: Int? = null,
    val userId: String,
)

fun AddTaskUiState.toDomain() = Task(
    id = id,
    name = name,
    isWorkRelated = isWorkRelated,
    ticketId = ticketId,
    userId = userId,
)
