package com.braiso_22.bichota_timer.work_hours_config.domain

import com.braiso_22.bichota_timer.work_hours_config.domain.entity.WorkHoursConfig
import kotlinx.coroutines.flow.Flow

interface WorkHoursConfigRepository {
    suspend fun addWorkHoursConfig(workHoursConfig: WorkHoursConfig)
    fun getWorkHoursConfigByUserId(userId: String): Flow<List<WorkHoursConfig>>
}