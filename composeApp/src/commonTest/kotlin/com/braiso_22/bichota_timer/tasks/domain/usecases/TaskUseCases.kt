package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.data.TaskRepositoryMock
import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import com.braiso_22.bichota_timer.tasks.domain.entities.Execution
import com.braiso_22.bichota_timer.tasks.domain.entities.Segment
import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import com.raedghazal.kotlinx_datetime_ext.now
import com.raedghazal.kotlinx_datetime_ext.plus
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.hours

class TaskUseCases {
    private lateinit var taskRepository: TaskRepository
    private lateinit var upsertTask: UpsertTask
    private lateinit var getTaskById: GetTaskById
    private lateinit var getExecutionById: GetExecutionById
    private lateinit var getSegmentById: GetSegmentById
    private lateinit var upsertExecution: UpsertExecution
    private lateinit var upsertSegment: UpsertSegment
    private lateinit var getTasksWithAllExecutionsByUser: GetTasksWithAllExecutionsByUser
    private lateinit var getTaskWithAllExecutions: GetTaskWithAllExecutions
    private lateinit var getWorkedHoursOfTasks: GetWorkedHoursOfTasks

    @BeforeTest
    fun setup() {
        taskRepository = TaskRepositoryMock()
        getTaskById = GetTaskById(taskRepository)
        upsertTask = UpsertTask(taskRepository, getTaskById = getTaskById)
        getExecutionById = GetExecutionById(taskRepository)
        upsertExecution = UpsertExecution(taskRepository, getExecutionById = getExecutionById)
        getSegmentById = GetSegmentById(taskRepository)
        upsertSegment = UpsertSegment(taskRepository, getSegmentById = getSegmentById)
        getTasksWithAllExecutionsByUser = GetTasksWithAllExecutionsByUser(taskRepository)
        getTaskWithAllExecutions = GetTaskWithAllExecutions(taskRepository)
        getWorkedHoursOfTasks = GetWorkedHoursOfTasks()
    }


    @Test
    fun `Get tasks by user returns only tasks of user`() = runBlocking {
        val newTask = Task(
            id = "1",
            name = "Task 1",
            isWorkRelated = true,
            creationDate = LocalDateTime.now(),
            ticketId = 1,
            userId = "user1"
        )

        val secondTask = Task(
            id = "2",
            name = "Task 2",
            isWorkRelated = true,
            creationDate = LocalDateTime.now(),
            ticketId = 1,
            userId = "user2"
        )

        upsertTask(newTask)
        upsertTask(secondTask)

        val tasks: List<Task> = getTasksWithAllExecutionsByUser("user1").take(1).first()

        assertEquals(1, tasks.size)
        assertContains(tasks, newTask)
    }

    @Test
    fun `Add execution without id returns same execution with new id`() = runBlocking {
        val execution = Execution(taskId = "1")
        val newExecution = upsertExecution(execution)
        assertTrue(newExecution.id != "")
        assertEquals(execution.copy(id = newExecution.id), newExecution)
    }

    @Test
    fun `Add executions and segments returns all when getTaskWithAllExecutions`() = runBlocking {
        val newTask = Task(
            id = "1",
            name = "Task 1",
            isWorkRelated = true,
            creationDate = LocalDateTime.now(),
            ticketId = 1,
            userId = "user1",
        )

        val newExecution = Execution(
            id = "1",
            taskId = newTask.id
        )
        val secondExecution = Execution(
            id = "2",
            taskId = newTask.id
        )

        val newSegment = Segment(
            id = "1",
            start = LocalTime.now(),
            end = (LocalDateTime.now() + 1.hours).time,
            executionId = newExecution.id
        )
        val segment2 = Segment(
            id = "2",
            start = (LocalDateTime.now() + 2.hours).time,
            executionId = secondExecution.id
        )

        upsertTask(newTask)
        upsertExecution(newExecution)
        upsertExecution(secondExecution)
        upsertSegment(newSegment)
        upsertSegment(segment2)

        val task = getTaskWithAllExecutions("1").take(1).first()

        assertNotNull(task)
        assertEquals(task.executions.size, 2)
        assertEquals(task.executions[0].id, "1")
        assertEquals(task.executions[1].id, "2")
        assertContains(task.executions[0].segments, newSegment)
        assertContains(task.executions[1].segments, segment2)
    }

    @Test
    fun `Add segment without id returns same segment with new id`() = runBlocking {
        val segment = Segment(executionId = "1")
        val newSegment = upsertSegment(segment)
        assertTrue(newSegment.id != "")
        assertEquals(segment.copy(id = newSegment.id), newSegment)
    }

    @Test
    fun `Add segment without id linked to task returns this execution`() = runBlocking {
        val task = Task(
            id = "1",
            name = "Task 1",
            isWorkRelated = true,
            creationDate = LocalDateTime.now(),
            ticketId = 1,
            userId = "user1",
        )
        val execution = Execution(
            id = "1",
            taskId = task.id
        )
        val segment = Segment(
            executionId = execution.id
        )
        upsertTask(task)
        upsertExecution(execution)
        val newSegment = upsertSegment(segment)

        val taskWithExecution = getTaskWithAllExecutions("1").take(1).first()
        assertNotNull(taskWithExecution)
        assertEquals(taskWithExecution.executions[0].segments[0].id, newSegment.id)
    }


    @Test
    fun `Not adding tasks returns null`() = runBlocking {
        val task = getTaskWithAllExecutions("1").take(1).first()

        assertNull(task)
    }

    @Test
    fun `Search by not existing tasks returns null`() = runBlocking {
        val newTask = Task(
            id = "1",
            name = "Task 1",
            isWorkRelated = true,
            creationDate = LocalDateTime.now(),
            ticketId = 1,
            userId = "user1",
        )
        upsertTask(newTask)
        val task = getTaskWithAllExecutions("2").take(1).first()

        assertNull(task)
    }
}