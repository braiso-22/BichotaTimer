package com.braiso_22.bichota_timer.work_hours_config.domain.usecases

import com.braiso_22.bichota_timer.work_hours_config.domain.WorkHoursConfigRepository

class GetConfigsByUserId(
    private val workHoursConfigRepository: WorkHoursConfigRepository
) {
    operator fun invoke(userId: String) =
        workHoursConfigRepository.getWorkHoursConfigByUserId(userId)
}