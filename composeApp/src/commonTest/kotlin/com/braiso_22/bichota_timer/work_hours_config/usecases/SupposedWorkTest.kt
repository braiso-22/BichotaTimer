package com.braiso_22.bichota_timer.work_hours_config.usecases

import com.braiso_22.bichota_timer.work_hours_config.data.WorkHoursConfigRepositoryMock
import com.braiso_22.bichota_timer.work_hours_config.domain.WorkHoursConfigRepository
import com.braiso_22.bichota_timer.work_hours_config.domain.entity.WorkHoursConfig
import com.braiso_22.bichota_timer.work_hours_config.domain.entity.sumOfHours
import com.braiso_22.bichota_timer.work_hours_config.domain.usecases.AddWorkHoursConfig
import com.braiso_22.bichota_timer.work_hours_config.domain.usecases.CheckForConflicts
import com.braiso_22.bichota_timer.work_hours_config.domain.usecases.GetConfigsByUserId
import com.braiso_22.bichota_timer.work_hours_config.domain.usecases.GetWorkDaysInRange
import com.raedghazal.kotlinx_datetime_ext.now
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SupposedWorkTest {
    private lateinit var workHoursConfigRepository: WorkHoursConfigRepository
    private lateinit var getConfigsByUserId: GetConfigsByUserId
    private lateinit var getWorkDaysInRange: GetWorkDaysInRange
    private lateinit var addWorkHoursConfigTest: AddWorkHoursConfig
    private lateinit var checkForConflicts: CheckForConflicts

    @BeforeTest
    fun setup() {
        workHoursConfigRepository = WorkHoursConfigRepositoryMock()
        getConfigsByUserId = GetConfigsByUserId(workHoursConfigRepository)
        checkForConflicts = CheckForConflicts()
        addWorkHoursConfigTest = AddWorkHoursConfig(
            workHoursConfigRepository,
            checkForConflicts,
            getConfigsByUserId
        )
        getWorkDaysInRange = GetWorkDaysInRange(getConfigsByUserId, checkForConflicts)
    }

    @Test
    fun `Get work days without config returns empty list`() = runBlocking {
        val workDays = getWorkDaysInRange(
            userId = "user1",
            from = LocalDate(2023, 1, 1),
            to = LocalDate.now()
        ).first()
        assertEquals(emptyList(), workDays)
    }

    @Test
    fun `Get work days with config returns work days`() = runBlocking {
        val config = WorkHoursConfig(
            id = "1",
            daysOfWeek = listOf(DayOfWeek.MONDAY),
            hoursPerDay = 8f,
            from = LocalDate(2023, 1, 1),
            to = LocalDate(2023, 1, 7),
            userId = "user1",
        )

        addWorkHoursConfigTest(config)
        val workDays = getWorkDaysInRange(
            userId = "user1",
            from = LocalDate(2023, 1, 1),
            to = LocalDate.now()
        ).first()

        assertEquals(1, workDays.size)
        assertEquals(8f, workDays[0].hours)
    }

    @Test
    fun `Range of 1 day returns that day inclusive`() = runBlocking {
        val config = WorkHoursConfig(
            id = "1",
            daysOfWeek = listOf(DayOfWeek.SATURDAY),
            hoursPerDay = 1f,
            from = LocalDate(2024, 6, 29),
            to = LocalDate(2024, 6, 29),
            userId = "user1",
        )

        addWorkHoursConfigTest(config)
        val workDays = getWorkDaysInRange(
            userId = "user1",
            from = LocalDate(2024, 6, 29),
            to = LocalDate(2024, 6, 29),
        ).first()

        assertEquals(1, workDays.size)
        assertEquals(1f, workDays[0].hours)
    }

    @Test
    fun `including last`() = runBlocking {
        val config = WorkHoursConfig(
            id = "1",
            daysOfWeek = listOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
            hoursPerDay = 1f,
            from = LocalDate(2024, 6, 29),
            to = LocalDate(2024, 6, 30),
            userId = "user1",
        )

        addWorkHoursConfigTest(config)
        val workDays = getWorkDaysInRange(
            userId = "user1",
            from = LocalDate(2024, 6, 29),
            to = LocalDate(2024, 6, 30),
        ).first()

        assertEquals(2, workDays.size)
        assertEquals(2f, workDays.sumOfHours())
    }
}