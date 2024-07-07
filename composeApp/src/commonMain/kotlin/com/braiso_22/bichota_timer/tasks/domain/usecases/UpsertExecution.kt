package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.benasher44.uuid.uuid4
import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import com.braiso_22.bichota_timer.tasks.domain.entities.Execution

class UpsertExecution(
    private val taskRepository: TaskRepository,
    private val getExecutionById: GetExecutionById,
) {
    suspend operator fun invoke(execution: Execution): Execution {
        val existingExecution: Execution? = getExecutionById(execution.id)

        val executionToInsert = if (existingExecution == null && execution.id == "") {
            execution.copy(id = uuid4().toString())
        } else {
            execution
        }
        taskRepository.upsertExecution(executionToInsert)
        return executionToInsert
    }
}