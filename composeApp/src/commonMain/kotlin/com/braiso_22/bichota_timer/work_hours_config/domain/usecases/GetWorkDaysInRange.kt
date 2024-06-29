package com.braiso_22.bichota_timer.work_hours_config.domain.usecases

import com.braiso_22.bichota_timer.work_hours_config.domain.entity.WorkDay
import com.raedghazal.kotlinx_datetime_ext.atStartOfDay
import com.raedghazal.kotlinx_datetime_ext.plus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.plus
import kotlinx.datetime.until
import kotlin.time.Duration.Companion.days

class GetWorkDaysInRange(
    private val getConfigsByUserId: GetConfigsByUserId,
    private val checkForConflicts: CheckForConflicts,
) {
    operator fun invoke(userId: String, from: LocalDate, to: LocalDate): Flow<List<WorkDay>> {
        return getConfigsByUserId(userId).map { configs ->
            val filteredConfig = configs.filter {
                val newConfig = it.copy(
                    id = "-1",
                    from = from,
                    to = to
                )
                checkForConflicts(newConfig, configs)
            }
            filteredConfig.flatMap { config ->
                val configRange = config.from..config.to
                val filtered = configRange.filter {
                    it in from..to && it.dayOfWeek in config.daysOfWeek
                }
                filtered.map {
                    WorkDay(
                        date = it,
                        hours = config.hoursPerDay
                    )
                }
            }
        }
    }
}

private operator fun LocalDate.rangeTo(that: LocalDate): List<LocalDate> = if (this == that) {
    listOf(this)
} else {
    val lastDay = (that.atStartOfDay() +  1.days).date
    List(this.until(lastDay, DateTimeUnit.DAY)) {
        this.plus(it, DateTimeUnit.DAY)
    }
}
