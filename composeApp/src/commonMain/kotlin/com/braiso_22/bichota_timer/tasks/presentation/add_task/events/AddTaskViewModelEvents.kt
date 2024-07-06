package com.braiso_22.bichota_timer.tasks.presentation.add_task.events

sealed class AddTaskViewModelEvents {
    data object OnSave : AddTaskViewModelEvents()
    data object OnCancel : AddTaskViewModelEvents()
}