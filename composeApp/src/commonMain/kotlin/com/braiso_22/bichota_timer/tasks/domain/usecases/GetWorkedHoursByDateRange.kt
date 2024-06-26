package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.domain.entities.Execution
import com.braiso_22.bichota_timer.tasks.domain.entities.Segment
import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class GetWorkedHoursByDateRange(
    private val getTasksWithAllExecutionsByUser: GetTasksWithAllExecutionsByUser
) {
    operator fun invoke(userId: String, from: LocalDate, to: LocalDate): Flow<Float> {
        return getTasksWithAllExecutionsByUser(userId)
            .map { tasks -> calculateTotalHours(tasks, from, to) }
    }

    private fun calculateTotalHours(tasks: List<Task>, from: LocalDate, to: LocalDate): Float {
        val totalMinutes = tasks.sumOf { task ->
            calculateTaskMinutes(task, from, to)
        }
        return totalMinutes.toFloat() / 60
    }

    private fun calculateTaskMinutes(task: Task, from: LocalDate, to: LocalDate): Long =
        task.executions
            .filter { it.date in from..to }
            .sumOf { execution ->
                execution.durationInMinutes()
            }


    private fun Execution.durationInMinutes(): Long = this.segments.sumOf { segment ->
        segment.durationInMinutes()
    }

    /**
    The duration if the end is null is 0 because
    we don't want to have it in account until its finished
     */
    private fun Segment.durationInMinutes(): Long = this.end?.let { end ->
        (end.toSecondOfDay() - this.start.toSecondOfDay()) / 60L
    } ?: 0
}
