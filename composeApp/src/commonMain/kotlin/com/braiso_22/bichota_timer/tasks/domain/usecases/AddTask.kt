package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import com.braiso_22.bichota_timer.tasks.domain.entities.Task

class AddTask(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task) = taskRepository.addTask(task)
}