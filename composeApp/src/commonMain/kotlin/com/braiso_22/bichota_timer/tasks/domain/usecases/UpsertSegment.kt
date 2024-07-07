package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.benasher44.uuid.uuid4
import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import com.braiso_22.bichota_timer.tasks.domain.entities.Segment

class UpsertSegment(
    private val taskRepository: TaskRepository,
    private val getSegmentById: GetSegmentById,
) {
    suspend operator fun invoke(segment: Segment): Segment {
        val existingSegment: Segment? = getSegmentById(segment.id)

        val segmentToInsert = if (existingSegment == null && segment.id == "") {
            segment.copy(id = uuid4().toString())
        } else {
            segment
        }
        taskRepository.addSegment(segmentToInsert)
        return segmentToInsert
    }
}
