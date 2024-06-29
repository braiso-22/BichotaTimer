package com.braiso_22.bichota_timer.work_hours_config.domain.usecases

import com.braiso_22.bichota_timer.work_hours_config.domain.WorkHoursConfigRepository
import com.braiso_22.bichota_timer.work_hours_config.domain.entity.WorkHoursConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CheckForConflicts(
    private val workHoursConfigRepository: WorkHoursConfigRepository
) {
    operator fun invoke(workHoursConfig: WorkHoursConfig): Flow<Boolean> {
        return workHoursConfigRepository.getWorkHoursConfigByUserId(workHoursConfig.userId)
            .map { configs ->
                configs.any {
                    it.id != workHoursConfig.id && hasOverlap(it, workHoursConfig)
                }
            }
    }

    private fun hasOverlap(existentConfig: WorkHoursConfig, newConfig: WorkHoursConfig): Boolean {
        val startsAfterNew = existentConfig.from > newConfig.to
        val endsBeforeNew = existentConfig.to < newConfig.from
        return !(startsAfterNew || endsBeforeNew)
    }
}