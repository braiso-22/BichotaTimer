package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.data.TaskRepositoryMock
import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import com.braiso_22.bichota_timer.tasks.domain.entities.Execution
import com.braiso_22.bichota_timer.tasks.domain.entities.Segment
import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import com.raedghazal.kotlinx_datetime_ext.atStartOfDay
import com.raedghazal.kotlinx_datetime_ext.minus
import com.raedghazal.kotlinx_datetime_ext.now
import com.raedghazal.kotlinx_datetime_ext.plus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

class GetWorkedHoursByDateRangeTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var upsertTask: UpsertTask
    private lateinit var getTaskById: GetTaskById
    private lateinit var getExecutionById: GetExecutionById
    private lateinit var getSegmentById: GetSegmentById
    private lateinit var upsertExecution: UpsertExecution
    private lateinit var upsertSegment: UpsertSegment
    private lateinit var getTasksWithAllExecutionsByUser: GetTasksWithAllExecutionsByUser
    private lateinit var getWorkedHoursOfTasks: GetWorkedHoursOfTasks
    private lateinit var getTasksWithExecutionsInDateRange: GetTasksWithExecutionsInDateRange

    @BeforeTest
    fun setup() {
        taskRepository = TaskRepositoryMock()
        getTaskById = GetTaskById(taskRepository)
        upsertTask = UpsertTask(taskRepository, getTaskById = getTaskById)
        getExecutionById = GetExecutionById(taskRepository)
        upsertExecution = UpsertExecution(taskRepository, getExecutionById = getExecutionById)
        getSegmentById = GetSegmentById(taskRepository)
        upsertSegment = UpsertSegment(taskRepository,getSegmentById)
        getTasksWithAllExecutionsByUser = GetTasksWithAllExecutionsByUser(taskRepository)
        getTasksWithExecutionsInDateRange =
            GetTasksWithExecutionsInDateRange(getTasksWithAllExecutionsByUser)
        getWorkedHoursOfTasks = GetWorkedHoursOfTasks()
    }

    @Test
    fun `GetWorked hours without segment returns 0`() = runBlocking {
        val newTask = Task(
            id = "1",
            name = "Task 1",
            isWorkRelated = true,
            creationDate = LocalDateTime.now(),
            userId = "user1"
        )

        upsertTask(newTask)
        val tasks = getTasksWithExecutionsInDateRange(
            userId = "user1",
            from = LocalDate(2024, 1, 1),
            to = (LocalDateTime.now() + 1.days).date
        ).first()
        val workedHours = getWorkedHoursOfTasks(tasks)
        assertEquals(0.0, workedHours)
    }

    @Test
    fun `GetWorked hours without finished segments returns 0`() = runBlocking {
        val newTask = Task(
            id = "1",
            name = "Task 1",
            isWorkRelated = true,
            creationDate = LocalDateTime.now(),
            userId = "user1"
        )
        upsertTask(newTask)

        val execution = Execution(
            id = "1",
            date = (LocalDateTime.now() - 1.days).date,
            taskId = "1"
        )
        upsertExecution(execution)

        val segment = Segment(
            id = "1",
            start = LocalTime.now(),
            end = null,
            executionId = "1"
        )
        upsertSegment(segment)

        val tasks = getTasksWithExecutionsInDateRange(
            userId = "user1",
            from = LocalDate(2024, 1, 1),
            to = (LocalDateTime.now() + 1.days).date
        ).first()
        val workedHours = getWorkedHoursOfTasks(tasks)

        assertEquals(0.0, workedHours)
    }

    @Test
    fun `GetWorked hours with segments not in the range returns only the correct ones`() =
        runBlocking {
            val todayAtStart = LocalDate.now().atStartOfDay()
            val firstOfYear = LocalDate(2024, 1, 1).atStartOfDay()
            val newTask = Task(
                id = "1",
                name = "Task 1",
                isWorkRelated = true,
                creationDate = todayAtStart,
                userId = "user1"
            )
            upsertTask(newTask)

            val execution = Execution(
                id = "1",
                date = (todayAtStart).date,
                taskId = "1"
            )
            upsertExecution(execution)
            val segment = Segment(
                id = "1",
                start = todayAtStart.time,
                end = (todayAtStart + 1.hours).time,
                executionId = "1"
            )
            upsertSegment(segment)

            val execution2 = Execution(
                id = "2",
                date = (todayAtStart + 1.days).date,
                taskId = "1"
            )
            upsertExecution(execution2)
            val segment2 = Segment(
                id = "2",
                start = (todayAtStart + 1.days).time,
                end = (todayAtStart + 1.days + 1.hours).time,
                executionId = "2"
            )
            upsertSegment(segment2)

            val execution3 = Execution(
                id = "3",
                date = (firstOfYear).date,
                taskId = "1"
            )
            upsertExecution(execution3)
            val segment3 = Segment(
                id = "3",
                start = firstOfYear.time,
                end = (firstOfYear + 1.hours).time,
                executionId = "3"
            )
            upsertSegment(segment3)

            val execution4 = Execution(
                id = "4",
                date = (firstOfYear - 1.days).date,
                taskId = "1"
            )
            upsertExecution(execution4)
            val segment4 = Segment(
                id = "4",
                start = (firstOfYear - 1.days).time,
                end = (firstOfYear - 1.days + 1.hours).time,
                executionId = "4"
            )
            upsertSegment(segment4)

            val tasks = getTasksWithExecutionsInDateRange(
                userId = "user1",
                from = firstOfYear.date,
                to = todayAtStart.date
            ).first()
            val workedHours = getWorkedHoursOfTasks(tasks)

            assertEquals(2.0, workedHours)
        }

    @Test
    fun `GetWorked hours with segment returns that duration`() = runBlocking {
        val todayAtStart = LocalDate.now().atStartOfDay()
        val newTask = Task(
            id = "1",
            name = "Task 1",
            isWorkRelated = true,
            creationDate = todayAtStart,
            userId = "user1"
        )
        upsertTask(newTask)

        val execution = Execution(
            id = "1",
            date = (todayAtStart - 1.days).date,
            taskId = "1"
        )
        upsertExecution(execution)

        val oneHourLongSegment = Segment(
            id = "1",
            start = todayAtStart.time,
            end = (todayAtStart + 1.hours).time,
            executionId = "1"
        )
        upsertSegment(oneHourLongSegment)

        val tasks = getTasksWithExecutionsInDateRange(
            userId = "user1",
            from = LocalDate(2024, 1, 1),
            to = (LocalDateTime.now() + 1.days).date
        ).first()
        val workedHours = getWorkedHoursOfTasks(tasks)

        assertEquals(1.0, workedHours)
    }

    @Test
    fun `GetWorked hours with several segment returns the sum of the duration`() = runBlocking {
        val todayAtStart = LocalDate.now().atStartOfDay()
        val newTask = Task(
            id = "1",
            name = "Task 1",
            isWorkRelated = true,
            creationDate = todayAtStart,
            userId = "user1"
        )
        upsertTask(newTask)

        val execution = Execution(
            id = "1",
            date = (todayAtStart - 1.days).date,
            taskId = "1"
        )
        upsertExecution(execution)

        val oneHourLongSegment = Segment(
            id = "1",
            start = todayAtStart.time,
            end = (todayAtStart + 1.hours).time,
            executionId = "1"
        )
        val twoHourLongSegment = Segment(
            id = "2",
            start = (todayAtStart + 1.hours).time,
            end = (todayAtStart + 3.hours).time,
            executionId = "1"
        )
        upsertSegment(oneHourLongSegment)
        upsertSegment(twoHourLongSegment)

        val tasks = getTasksWithExecutionsInDateRange(
            userId = "user1",
            from = LocalDate(2024, 1, 1),
            to = (LocalDateTime.now() + 1.days).date
        ).first()
        val workedHours = getWorkedHoursOfTasks(tasks)

        assertEquals(3.0, workedHours)
    }

    @Test
    fun `GetWorked hours with half hour segment returns the sum with decimals`() = runBlocking {
        val todayAtStart = LocalDate.now().atStartOfDay()
        val newTask = Task(
            id = "1",
            name = "Task 1",
            isWorkRelated = true,
            creationDate = todayAtStart,
            userId = "user1"
        )
        upsertTask(newTask)

        val execution = Execution(
            id = "1",
            date = (todayAtStart - 1.days).date,
            taskId = "1"
        )
        upsertExecution(execution)

        val oneHourLongSegment = Segment(
            id = "1",
            start = todayAtStart.time,
            end = (todayAtStart + 1.hours).time,
            executionId = "1"
        )
        val twoHourLongSegment = Segment(
            id = "2",
            start = (todayAtStart + 1.hours).time,
            end = (todayAtStart + 1.hours + 30.minutes).time,
            executionId = "1"
        )
        upsertSegment(oneHourLongSegment)
        upsertSegment(twoHourLongSegment)

        val tasks = getTasksWithExecutionsInDateRange(
            userId = "user1",
            from = LocalDate(2024, 1, 1),
            to = (LocalDateTime.now() + 1.days).date
        ).first()
        val workedHours = getWorkedHoursOfTasks(tasks)

        assertEquals(1.5, workedHours)
    }
}