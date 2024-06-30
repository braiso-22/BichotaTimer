package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

class GetTasksWithExecutionsInDateRange(
    private val getTasksWithAllExecutionsByUser: GetTasksWithAllExecutionsByUser
) {
    operator fun invoke(
        userId: String,
        from: LocalDate,
        to: LocalDate = from
    ): Flow<List<Task>> = getTasksWithAllExecutionsByUser(userId).map { tasks ->
        tasks.map { task ->
            task.copy(
                executions = task.executions.filter { execution ->
                    execution.date in from..to
                }
            )
        }.filter {
            it.executions.isNotEmpty()
        }
    }
}