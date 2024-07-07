package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import kotlinx.coroutines.flow.first

class GetExecutionById(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(id: String) = taskRepository.getExecutionById(id).first()
}