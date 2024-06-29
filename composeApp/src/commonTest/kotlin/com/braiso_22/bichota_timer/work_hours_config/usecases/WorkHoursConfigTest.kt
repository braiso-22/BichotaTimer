package com.braiso_22.bichota_timer.work_hours_config.usecases

import com.braiso_22.bichota_timer.work_hours_config.data.WorkHoursConfigRepositoryMock
import com.braiso_22.bichota_timer.work_hours_config.domain.WorkHoursConfigRepository
import com.braiso_22.bichota_timer.work_hours_config.domain.entity.WorkHoursConfig
import com.braiso_22.bichota_timer.work_hours_config.domain.usecases.AddWorkHoursConfig
import com.braiso_22.bichota_timer.work_hours_config.domain.usecases.CheckForConflicts
import com.braiso_22.bichota_timer.work_hours_config.domain.usecases.GetConfigsByUserId
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class WorkHoursConfigTest {

    private lateinit var workHoursConfigRepository: WorkHoursConfigRepository
    private lateinit var checkForConflicts: CheckForConflicts
    private lateinit var addWorkHoursConfigTest: AddWorkHoursConfig
    private lateinit var getConfigsByUserId: GetConfigsByUserId

    @BeforeTest
    fun setup() {
        workHoursConfigRepository = WorkHoursConfigRepositoryMock()
        checkForConflicts = CheckForConflicts()
        getConfigsByUserId = GetConfigsByUserId(workHoursConfigRepository)
        addWorkHoursConfigTest = AddWorkHoursConfig(
            workHoursConfigRepository,
            checkForConflicts,
            getConfigsByUserId,
        )
    }

    @Test
    fun `Add a config on start returns only one`() = runBlocking {
        val config = WorkHoursConfig(
            id = "1",
            daysOfWeek = listOf(),
            hoursPerDay = 8f,
            from = LocalDate(2024, 1, 1),
            to = LocalDate(2024, 1, 1),
            userId = "user1",
        )
        addWorkHoursConfigTest(config)
        val configs = getConfigsByUserId("user1").first()
        assertEquals(1, configs.size)
    }

    @Test
    fun `Add several configs returns only the user ones`() = runBlocking {
        val configsToAdd = List(3) {
            WorkHoursConfig(
                id = "$it",
                daysOfWeek = listOf(),
                hoursPerDay = 8f,
                from = LocalDate(2024, 1, 1),
                to = LocalDate(2024, 2, 1),
                userId = "user$it",
            )
        }
        configsToAdd.forEach {
            try {
                addWorkHoursConfigTest(it)
            } catch (e: Exception) {
                println(e.message)
            }
        }
        val configs = getConfigsByUserId("user1").first()
        assertEquals(1, configs.size)
    }

    @Test
    fun `Add conflicting configs throws exception`() = runBlocking {

        val configsToAdd = List(3) {
            WorkHoursConfig(
                id = "$it",
                daysOfWeek = listOf(),
                hoursPerDay = 8f,
                from = LocalDate(2024, 1, it + 3),
                to = LocalDate(2024, 1, it + 4),
                userId = "user1",
            )
        } + WorkHoursConfig(
            id = "3",
            daysOfWeek = listOf(),
            hoursPerDay = 8f,
            from = LocalDate(2024, 1, 1),
            to = LocalDate(2024, 1, 30),
            userId = "user1",
        )
        addWorkHoursConfigTest(configsToAdd[1])

        assertFails { addWorkHoursConfigTest(configsToAdd[0]) }
        assertFails { addWorkHoursConfigTest(configsToAdd[2]) }

        val configs = getConfigsByUserId("user1").first()
        assertEquals(1, configs.size)
    }

    @Test
    fun `Add configs that continue next day returns all`() = runBlocking {
        var lastDay = 0
        val configsToAdd = List(3) {
            val config = WorkHoursConfig(
                id = "$it",
                daysOfWeek = listOf(),
                hoursPerDay = 8f,
                from = LocalDate(2024, 1, lastDay + 1),
                to = LocalDate(2024, 1, lastDay + 2),
                userId = "user1",
            )
            lastDay += 2
            config
        } + WorkHoursConfig(
            id = "3",
            daysOfWeek = listOf(),
            hoursPerDay = 8f,
            from = LocalDate(2023, 1, 1),
            to = LocalDate(2023, 1, 2),
            userId = "user1",
        )
        configsToAdd.forEach {
            try {
                addWorkHoursConfigTest(it)
            } catch (e: Exception) {
                println(e.message)
            }
        }

        val configs = getConfigsByUserId("user1").first()
        assertEquals(configsToAdd.size, configs.size)
    }

    @Test
    fun `Add a already existing config updates it`() = runBlocking {
        val configsToAdd = List(3) {
            WorkHoursConfig(
                id = "1",
                daysOfWeek = listOf(),
                hoursPerDay = 8f,
                from = LocalDate(2024, 1, it + 1),
                to = LocalDate(2024, 1, it + 2),
                userId = "user1",
            )
        }

        configsToAdd.forEach {
            addWorkHoursConfigTest(it)
        }
        val configs = getConfigsByUserId("user1").first()
        assertEquals(1, configs.size)

    }
}