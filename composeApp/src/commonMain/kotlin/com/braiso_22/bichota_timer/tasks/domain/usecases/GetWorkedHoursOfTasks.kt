package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.domain.entities.Execution
import com.braiso_22.bichota_timer.tasks.domain.entities.Segment
import com.braiso_22.bichota_timer.tasks.domain.entities.Task

class GetWorkedHoursOfTasks {
    operator fun invoke(tasks: List<Task>): Double = tasks.getTotalHours()

    private fun List<Task>.getTotalHours(): Double {
        val sumOfMinutes = this.sumOf { task -> task.totalMinutes() }
        return sumOfMinutes / 60
    }

    private fun Task.totalMinutes(): Double = this.executions.sumOf { execution ->
        execution.totalMinutes()
    }

    private fun Execution.totalMinutes(): Double = this.segments.sumOf { segment ->
        segment.totalMinutes()
    }

    /**
    The duration is 0 if the end is null because
    we don't want to have it in account until its finished
     */
    private fun Segment.totalMinutes(): Double = this.end?.let { end ->
        (end.toSecondOfDay() - this.start.toSecondOfDay()).toDouble() / 60
    } ?: 0.0
}
