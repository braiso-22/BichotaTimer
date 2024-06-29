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
    private lateinit var addTask: AddTask
    private lateinit var addExecution: AddExecution
    private lateinit var addSegment: AddSegment
    private lateinit var getTasksWithAllExecutionsByUser: GetTasksWithAllExecutionsByUser
    private lateinit var getWorkedHoursOfTasks: GetWorkedHoursOfTasks
    private lateinit var getTasksWithExecutionsInDateRange: GetTasksWithExecutionsInDateRange

    @BeforeTest
    fun setup() {
        taskRepository = TaskRepositoryMock()
        addTask = AddTask(taskRepository)
        addExecution = AddExecution(taskRepository)
        addSegment = AddSegment(taskRepository)
        getTasksWithAllExecutionsByUser = GetTasksWithAllExecutionsByUser(taskRepository)
        getTasksWithExecutionsInDateRange = GetTasksWithExecutionsInDateRange(getTasksWithAllExecutionsByUser)
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

        addTask(newTask)
        val tasks = getTasksWithExecutionsInDateRange(
            userId = "user1",
            from = LocalDate(2024, 1, 1),
            to = (LocalDateTime.now() + 1.days).date
        ).first()
        val workedHours = getWorkedHoursOfTasks(tasks)
        assertEquals(0f, workedHours)
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
        addTask(newTask)

        val execution = Execution(
            id = "1",
            date = (LocalDateTime.now() - 1.days).date,
            taskId = "1"
        )
        addExecution(execution)

        val segment = Segment(
            id = "1",
            start = LocalTime.now(),
            end = null,
            executionId = "1"
        )
        addSegment(segment)

        val tasks = getTasksWithExecutionsInDateRange(
            userId = "user1",
            from = LocalDate(2024, 1, 1),
            to = (LocalDateTime.now() + 1.days).date
        ).first()
        val workedHours = getWorkedHoursOfTasks(tasks)

        assertEquals(0f, workedHours)
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
            addTask(newTask)

            val execution = Execution(
                id = "1",
                date = (todayAtStart).date,
                taskId = "1"
            )
            addExecution(execution)
            val segment = Segment(
                id = "1",
                start = todayAtStart.time,
                end = (todayAtStart + 1.hours).time,
                executionId = "1"
            )
            addSegment(segment)

            val execution2 = Execution(
                id = "2",
                date = (todayAtStart + 1.days).date,
                taskId = "1"
            )
            addExecution(execution2)
            val segment2 = Segment(
                id = "2",
                start = (todayAtStart + 1.days).time,
                end = (todayAtStart + 1.days + 1.hours).time,
                executionId = "2"
            )
            addSegment(segment2)

            val execution3 = Execution(
                id = "3",
                date = (firstOfYear).date,
                taskId = "1"
            )
            addExecution(execution3)
            val segment3 = Segment(
                id = "3",
                start = firstOfYear.time,
                end = (firstOfYear + 1.hours).time,
                executionId = "3"
            )
            addSegment(segment3)

            val execution4 = Execution(
                id = "4",
                date = (firstOfYear - 1.days).date,
                taskId = "1"
            )
            addExecution(execution4)
            val segment4 = Segment(
                id = "4",
                start = (firstOfYear - 1.days).time,
                end = (firstOfYear - 1.days + 1.hours).time,
                executionId = "4"
            )
            addSegment(segment4)

            val tasks = getTasksWithExecutionsInDateRange(
                userId = "user1",
                from = firstOfYear.date,
                to = todayAtStart.date
            ).first()
            val workedHours = getWorkedHoursOfTasks(tasks)

            assertEquals(2f, workedHours)
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
        addTask(newTask)

        val execution = Execution(
            id = "1",
            date = (todayAtStart - 1.days).date,
            taskId = "1"
        )
        addExecution(execution)

        val oneHourLongSegment = Segment(
            id = "1",
            start = todayAtStart.time,
            end = (todayAtStart + 1.hours).time,
            executionId = "1"
        )
        addSegment(oneHourLongSegment)

        val tasks = getTasksWithExecutionsInDateRange(
            userId = "user1",
            from = LocalDate(2024, 1, 1),
            to = (LocalDateTime.now() + 1.days).date
        ).first()
        val workedHours = getWorkedHoursOfTasks(tasks)

        assertEquals(1f, workedHours)
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
        addTask(newTask)

        val execution = Execution(
            id = "1",
            date = (todayAtStart - 1.days).date,
            taskId = "1"
        )
        addExecution(execution)

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
        addSegment(oneHourLongSegment)
        addSegment(twoHourLongSegment)

        val tasks = getTasksWithExecutionsInDateRange(
            userId = "user1",
            from = LocalDate(2024, 1, 1),
            to = (LocalDateTime.now() + 1.days).date
        ).first()
        val workedHours = getWorkedHoursOfTasks(tasks)

        assertEquals(3f, workedHours)
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
        addTask(newTask)

        val execution = Execution(
            id = "1",
            date = (todayAtStart - 1.days).date,
            taskId = "1"
        )
        addExecution(execution)

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
        addSegment(oneHourLongSegment)
        addSegment(twoHourLongSegment)

        val tasks = getTasksWithExecutionsInDateRange(
            userId = "user1",
            from = LocalDate(2024, 1, 1),
            to = (LocalDateTime.now() + 1.days).date
        ).first()
        val workedHours = getWorkedHoursOfTasks(tasks)

        assertEquals(1.5f, workedHours)
    }
}