package com.braiso_22.bichota_timer.work_hours_config.data

import com.braiso_22.bichota_timer.work_hours_config.domain.WorkHoursConfigRepository
import com.braiso_22.bichota_timer.work_hours_config.domain.entity.WorkHoursConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class WorkHoursConfigRepositoryMock : WorkHoursConfigRepository {
    private val _configs = MutableStateFlow<List<WorkHoursConfig>>(emptyList())
    private val configs: StateFlow<List<WorkHoursConfig>> = _configs.asStateFlow()

    private fun <T> updateElementInList(currents: List<T>, index: Int, element: T): List<T> =
        if (index != -1) {
            currents.toMutableList().apply {
                set(index, element)
            }
        } else {
            currents + element
        }

    override suspend fun addWorkHoursConfig(workHoursConfig: WorkHoursConfig) {
        _configs.update { currents ->
            val existingIndex = currents.indexOfFirst { it.id == workHoursConfig.id }

            updateElementInList(currents, existingIndex, workHoursConfig)
        }
    }

    override fun getWorkHoursConfigByUserId(userId: String): Flow<List<WorkHoursConfig>> =
        configs.map { config ->
            config.filter {
                it.userId == userId
            }
        }
}