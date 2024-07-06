package com.braiso_22.bichota_timer.tasks.presentation.add_task.events

sealed class AddTaskUiEvent {
    data class NameChanged(val name: String) : AddTaskUiEvent()
    data class IsWorkRelatedChanged(val isWorkRelated: Boolean) : AddTaskUiEvent()
    data class TicketIdChanged(val ticketId: String) : AddTaskUiEvent()
    data object Save : AddTaskUiEvent()
    data object Cancel : AddTaskUiEvent()
}