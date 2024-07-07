package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import kotlinx.coroutines.flow.first

class GetTaskById(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(id: String): Task? {
        return taskRepository.getTaskById(id).first()
    }
}