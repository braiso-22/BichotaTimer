package com.braiso_22.bichota_timer.tasks.domain.usecases

import com.braiso_22.bichota_timer.tasks.domain.entities.Execution
import com.braiso_22.bichota_timer.tasks.domain.entities.Segment
import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import com.raedghazal.kotlinx_datetime_ext.now
import com.raedghazal.kotlinx_datetime_ext.plus
import kotlinx.datetime.LocalDateTime
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.minutes

class GetCategorizedTasksTest {
    private lateinit var getCategorizedTasks: GetCategorizedTasks

    @BeforeTest
    fun setup() {
        getCategorizedTasks = GetCategorizedTasks()
    }

    @Test
    fun `Empty list returns empty categories`() {
        val tasks = emptyList<Task>()
        val categories = getCategorizedTasks(tasks)
        assertEquals(0, categories.completed.size)
        assertEquals(0, categories.running.size)
        assertEquals(0, categories.pending.size)
    }

    @Test
    fun `One completed task returns one completed and rest empty`() {
        val tasks = listOf(Task(userId = "1", isCompleted = true))
        val categories = getCategorizedTasks(tasks)
        assertEquals(1, categories.completed.size)
        assertEquals(0, categories.running.size)
        assertEquals(0, categories.pending.size)
    }

    @Test
    fun `One inCompleted with ended segment returns 1 in pending`() {
        val segment = Segment(
            id = "1",
            end = (LocalDateTime.now() + 1.minutes).time,
            executionId = "1"
        )
        val execution = Execution(id = "1", segments = listOf(segment), taskId = "1")
        val tasks = listOf(Task(userId = "1", isCompleted = false, executions = listOf(execution)))
        val categories = getCategorizedTasks(tasks)
        assertEquals(0, categories.completed.size)
        assertEquals(0, categories.running.size)
        assertEquals(1, categories.pending.size)
    }
    @Test
    fun `One inCompleted without ended segment returns 1 in pending`() {
        val segment = Segment(
            id = "1",
            executionId = "1"
        )
        val execution = Execution(id = "1", segments = listOf(segment), taskId = "1")
        val tasks = listOf(Task(userId = "1", isCompleted = false, executions = listOf(execution)))
        val categories = getCategorizedTasks(tasks)
        assertEquals(0, categories.completed.size)
        assertEquals(1, categories.running.size)
        assertEquals(0, categories.pending.size)
    }
}