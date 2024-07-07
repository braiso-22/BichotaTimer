package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import com.braiso_22.bichota_timer.tasks.domain.entities.Segment
import kotlinx.coroutines.flow.first

class GetSegmentById(
    private val taskRepository: TaskRepository,
) {
    suspend operator fun invoke(id: String): Segment? = taskRepository.getSegmentById(id).first()
}
