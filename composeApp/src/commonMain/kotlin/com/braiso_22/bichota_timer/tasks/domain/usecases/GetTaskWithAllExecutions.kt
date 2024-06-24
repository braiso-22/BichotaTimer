package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow

class GetTaskWithAllExecutions(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(taskId: String): Flow<Task?> = flow {
        val taskFlow = taskRepository.getTaskById(taskId)
        val executionsFlow = taskRepository.getExecutionsByTaskId(taskId)

        combine(taskFlow, executionsFlow) { task, executions ->
            task?.copy(executions = executions)
        }.collect { taskWithExecutions ->
            emit(taskWithExecutions)
        }
    }
}