package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.data.TaskRepositoryMock
import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import com.raedghazal.kotlinx_datetime_ext.now
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDateTime
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class UpsertTaskTest {

    private lateinit var taskRepository: TaskRepository
    private lateinit var upsertTask: UpsertTask
    private lateinit var getTaskById: GetTaskById
    private lateinit var getTasksWithAllExecutionsByUser: GetTasksWithAllExecutionsByUser

    @BeforeTest
    fun setup() {
        taskRepository = TaskRepositoryMock()
        getTaskById = GetTaskById(taskRepository)
        upsertTask = UpsertTask(taskRepository, getTaskById)
        getTasksWithAllExecutionsByUser = GetTasksWithAllExecutionsByUser(taskRepository)
    }

    @Test
    fun `Add task returns only that task`() = runBlocking {
        val newTask = Task(
            id = "1",
            name = "Task 1",
            isWorkRelated = true,
            creationDate = LocalDateTime.now(),
            ticketId = 1,
            userId = "user1"
        )

        upsertTask(newTask)

        val tasks = getTasksWithAllExecutionsByUser("user1").take(1).first()

        assertEquals(1, tasks.size)
        assertContains(tasks, newTask)
    }

    @Test
    fun `Add task with same id returns only that task`() = runBlocking {
        val newTask = Task(
            id = "1",
            name = "Task 1",
            isWorkRelated = true,
            creationDate = LocalDateTime.now(),
            ticketId = 1,
            userId = "user1"
        )
        val secondTask = Task(
            id = "1",
            name = "Task 2",
            isWorkRelated = true,
            creationDate = LocalDateTime.now(),
            ticketId = 1,
            userId = "user1"
        )

        upsertTask(newTask)
        upsertTask(secondTask)

        val tasks = getTasksWithAllExecutionsByUser("user1").take(1).first()

        assertEquals(1, tasks.size)
        assertContains(tasks, secondTask)
    }

    @Test
    fun `Add task without id returns same task with new id`() = runBlocking {
        val task = Task(
            id = "",
            name = "Task 1",
            isWorkRelated = true,
            creationDate = LocalDateTime.now(),
            ticketId = 1,
            userId = "user1"
        )
        val newTask = upsertTask(task)

        assertEquals(task.copy(id = newTask.id), newTask)
    }
}