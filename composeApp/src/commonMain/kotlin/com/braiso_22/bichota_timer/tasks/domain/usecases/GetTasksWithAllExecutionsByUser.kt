package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class GetTasksWithAllExecutionsByUser(
    private val taskRepository: TaskRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(userId: String): Flow<List<Task>> {
        val tasksFlow = taskRepository.getTasksByUserId(userId)
        return tasksFlow.flatMapLatest { tasks ->
            val executionsFlows = tasks.map { task ->
                taskRepository.getExecutionsByTaskId(task.id).map { executions ->
                    task.copy(executions = executions)
                }
            }
            combine(executionsFlows) { taskWithExecutionsArray ->
                taskWithExecutionsArray.toList()
            }
        }
    }
}