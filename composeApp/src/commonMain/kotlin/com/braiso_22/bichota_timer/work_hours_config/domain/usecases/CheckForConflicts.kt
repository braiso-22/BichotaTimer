package com.braiso_22.bichota_timer.work_hours_config.domain.usecases

import com.braiso_22.bichota_timer.work_hours_config.domain.entity.WorkHoursConfig

class CheckForConflicts {
    operator fun invoke(
        newConfig: WorkHoursConfig,
        existentConfigs: List<WorkHoursConfig>
    ): Boolean = existentConfigs.any {
        it.id != newConfig.id && hasOverlap(it, newConfig)
    }

    private fun hasOverlap(existentConfig: WorkHoursConfig, newConfig: WorkHoursConfig): Boolean {
        val startsAfterNew = existentConfig.from > newConfig.to
        val endsBeforeNew = existentConfig.to < newConfig.from
        return !(startsAfterNew || endsBeforeNew)
    }
}