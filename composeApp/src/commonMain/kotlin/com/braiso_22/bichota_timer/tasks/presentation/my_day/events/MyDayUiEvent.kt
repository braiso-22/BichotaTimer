package com.braiso_22.bichota_timer.tasks.presentation.my_day.events

sealed class MyDayUiEvent {
    data class StartTask(val taskId: String) : MyDayUiEvent()
    data class StopTask(val taskId: String) : MyDayUiEvent()
}