package com.braiso_22.bichota_timer.work_hours_config.domain.usecases

import com.braiso_22.bichota_timer.work_hours_config.domain.WorkHoursConfigRepository
import com.braiso_22.bichota_timer.work_hours_config.domain.entity.WorkHoursConfig
import kotlinx.coroutines.flow.first

class AddWorkHoursConfig(
    private val workHoursConfigRepository: WorkHoursConfigRepository,
    private val checkForConflicts: CheckForConflicts
) {
    suspend operator fun invoke(workHoursConfig: WorkHoursConfig) {
        val hasOverlap = checkForConflicts(workHoursConfig).first()

        if (!hasOverlap) {
            workHoursConfigRepository.addWorkHoursConfig(workHoursConfig)
        } else {
            throw IllegalArgumentException("Config conflict")
        }
    }
}
