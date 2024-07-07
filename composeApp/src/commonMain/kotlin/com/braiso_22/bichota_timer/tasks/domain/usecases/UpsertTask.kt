package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.benasher44.uuid.uuid4
import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import com.braiso_22.bichota_timer.tasks.domain.entities.Task

class UpsertTask(
    private val taskRepository: TaskRepository,
    private val getTaskById: GetTaskById,
) {
    suspend operator fun invoke(task: Task): Task {
        val existingTask: Task? = getTaskById(task.id)

        val taskToInsert = if (existingTask == null && task.id == "") {
            task.copy(id = uuid4().toString())
        } else {
            task
        }
        taskRepository.addTask(taskToInsert)
        return taskToInsert
    }
}