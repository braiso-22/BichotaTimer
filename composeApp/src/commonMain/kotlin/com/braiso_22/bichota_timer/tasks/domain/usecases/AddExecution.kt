package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import com.braiso_22.bichota_timer.tasks.domain.entities.Execution

class AddExecution(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(execution: Execution) = taskRepository.addExecution(execution)
}