package com.braiso_22.bichota_timer.tasks.domain

import com.braiso_22.bichota_timer.tasks.domain.entities.Execution
import com.braiso_22.bichota_timer.tasks.domain.entities.Segment
import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun addTask(task: Task)
    suspend fun upsertExecution(execution: Execution)
    suspend fun addSegment(segment: Segment)
    fun getTaskById(taskId: String): Flow<Task?>
    fun getExecutionById(id: String): Flow<Execution?>
    fun getSegmentById(id: String): Flow<Segment?>
    fun getTasksByUserId(userId: String): Flow<List<Task>>
    fun getExecutionsByTaskId(taskId: String): Flow<List<Execution>>
}
