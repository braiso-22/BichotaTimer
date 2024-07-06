package com.braiso_22.bichota_timer.tasks.presentation.my_day.mappers

import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import com.braiso_22.bichota_timer.tasks.domain.usecases.GetWorkedHoursOfTasks
import com.braiso_22.bichota_timer.tasks.presentation.my_day.state.TaskUiState

fun Task.toUiState() = TaskUiState(
    ticket = ticketId,
    title = name,
    isWorkRelated = isWorkRelated,
    duration = GetWorkedHoursOfTasks().invoke(listOf(this)).toHoursAndMinutes()
)

fun Float.toHoursAndMinutes(): String {
    val hours = this.toInt()
    val minutes = (this * 60).toInt() % 60

    return "${hours.parseTime()}:${minutes.parseTime()}"
}

fun Int.parseTime(): String = if (this < 10) {
    "0$this"
} else {
    this.toString()
}
