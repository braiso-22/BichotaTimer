package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.domain.entities.Execution
import com.braiso_22.bichota_timer.tasks.domain.entities.Segment
import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import com.raedghazal.kotlinx_datetime_ext.now
import kotlinx.datetime.LocalTime

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

    private fun Segment.totalMinutes(): Double = (this.end ?: LocalTime.now()).let { end ->
        (end.toSecondOfDay() - this.start.toSecondOfDay()).toDouble() / 60
    }
}
