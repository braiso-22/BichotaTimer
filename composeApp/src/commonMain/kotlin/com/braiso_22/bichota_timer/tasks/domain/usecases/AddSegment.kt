package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import com.braiso_22.bichota_timer.tasks.domain.entities.Segment

class AddSegment(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(segment: Segment) = taskRepository.addSegment(segment)
}