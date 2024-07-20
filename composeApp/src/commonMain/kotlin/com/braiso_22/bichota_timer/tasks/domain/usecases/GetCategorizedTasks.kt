package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.domain.entities.CategorizedTasks
import com.braiso_22.bichota_timer.tasks.domain.entities.Task

class GetCategorizedTasks {
    operator fun invoke(tasks: List<Task>): CategorizedTasks {
        val (completed, notCompleted) = tasks.partition { it.isCompleted }
        val (running, pending) = notCompleted.partition { task ->
            task.executions.any { execution ->
                execution.segments.any { segment ->
                    segment.end == null
                }
            }
        }
        return CategorizedTasks(completed = completed, running = running, pending = pending)
    }
}