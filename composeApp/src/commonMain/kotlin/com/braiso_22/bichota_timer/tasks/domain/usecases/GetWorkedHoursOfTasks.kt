package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.domain.entities.Execution
import com.braiso_22.bichota_timer.tasks.domain.entities.Segment
import com.braiso_22.bichota_timer.tasks.domain.entities.Task

class GetWorkedHoursOfTasks {
    operator fun invoke(tasks: List<Task>): Float = tasks.getTotalHours()

    private fun List<Task>.getTotalHours(): Float {
        val sumOfMinutes = this.sumOf { task -> task.totalMinutes() }
        return sumOfMinutes.toFloat() / 60
    }

    private fun Task.totalMinutes(): Int = this.executions.sumOf { execution ->
        execution.totalMinutes()
    }

    private fun Execution.totalMinutes(): Int = this.segments.sumOf { segment ->
        segment.totalMinutes()
    }

    /**
    The duration is 0 if the end is null because
    we don't want to have it in account until its finished
     */
    private fun Segment.totalMinutes(): Int = this.end?.let { end ->
        (end.toSecondOfDay() - this.start.toSecondOfDay()) / 60
    } ?: 0
}
