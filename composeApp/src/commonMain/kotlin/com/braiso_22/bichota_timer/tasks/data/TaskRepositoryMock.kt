package com.braiso_22.bichota_timer.tasks.data

import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import com.braiso_22.bichota_timer.tasks.domain.entities.Execution
import com.braiso_22.bichota_timer.tasks.domain.entities.Segment
import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TaskRepositoryMock : TaskRepository {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    private val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _executions = MutableStateFlow<List<Execution>>(emptyList())
    private val executions = _executions.asStateFlow()

    private val _segments = MutableStateFlow<List<Segment>>(emptyList())
    private val segments = _segments.asStateFlow()

    private fun <T : Any> upsertItemInList(
        items: List<T>,
        getItemIndex: (T) -> Boolean,
        newItem: T,
    ): List<T> {
        val index = items.indexOfFirst(getItemIndex)
        return if (index != -1) {
            items.toMutableList().apply {
                set(index, newItem)
            }
        } else {
            items + newItem
        }
    }


    override suspend fun addTask(task: Task) {
        _tasks.update { currents ->
            upsertItemInList(
                items = currents,
                getItemIndex = { task.id == it.id },
                newItem = task,
            )
        }
    }


    override suspend fun upsertExecution(execution: Execution) {
        _executions.update { currents ->
            upsertItemInList(
                items = currents,
                getItemIndex = { it.id == execution.id },
                newItem = execution,
            )
        }
    }

    override suspend fun addSegment(segment: Segment) {
        _segments.update { currents ->
            upsertItemInList(
                items = currents,
                getItemIndex = { it.id == segment.id },
                newItem = segment,
            )
        }
    }

    override fun getTaskById(taskId: String): Flow<Task?> = tasks.map {
        it.find { task ->
            task.id == taskId
        }
    }

    override fun getExecutionById(id: String): Flow<Execution?> = executions.map {
        it.find { execution ->
            execution.id == id
        }
    }

    override fun getSegmentById(id: String): Flow<Segment?> = segments.map {
        it.find { segment ->
            segment.id == id
        }
    }

    override fun getTasksByUserId(userId: String): Flow<List<Task>> = tasks.map {
        it.filter { task ->
            task.userId == userId
        }
    }

    override fun getExecutionsByTaskId(taskId: String): Flow<List<Execution>> = executions.map {
        it.filter { execution ->
            execution.taskId == taskId
        }
    }.combine(segments) { executions, segments ->
        executions.map { execution ->
            execution.copy(segments = segments.filter { it.executionId == execution.id })
        }
    }
}