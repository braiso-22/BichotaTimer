package com.braiso_22.bichota_timer.tasks.domain

import com.braiso_22.bichota_timer.tasks.domain.entities.Execution
import com.braiso_22.bichota_timer.tasks.domain.entities.Segment
import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(task: Task): Task
    suspend fun addExecution(execution: Execution): Execution
    suspend fun addSegment(segment: Segment): Segment
    fun getTaskById(taskId: String): Flow<Task?>
    fun getTasksByUserId(userId: String): Flow<List<Task>>
    fun getExecutionsByTaskId(taskId: String): Flow<List<Execution>>
}
