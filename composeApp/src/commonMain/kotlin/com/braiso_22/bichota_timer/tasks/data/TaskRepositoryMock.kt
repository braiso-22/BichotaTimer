package com.braiso_22.bichota_timer.tasks.data

import com.braiso_22.bichota_timer.tasks.domain.TaskRepository
import com.braiso_22.bichota_timer.tasks.domain.entities.Execution
import com.braiso_22.bichota_timer.tasks.domain.entities.Segment
import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class TaskRepositoryMock : TaskRepository {
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    private val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _executions = MutableStateFlow<List<Execution>>(emptyList())
    private val executions = _executions.asStateFlow()

    private val _segments = MutableStateFlow<List<Segment>>(emptyList())
    private val segments = _segments.asStateFlow()

    private fun <T> updateElementInList(currents: List<T>, index: Int, element: T): List<T> =
        if (index != -1) {
            currents.toMutableList().apply {
                set(index, element)
            }
        } else {
            currents + element
        }


    override suspend fun addTask(task: Task) = _tasks.update { currents ->
        val existingIndex = currents.indexOfFirst { it.id == task.id }
        updateElementInList(currents, existingIndex, task)
    }


    override suspend fun addExecution(execution: Execution) = _executions.update { currents ->
        val existingIndex = currents.indexOfFirst { it.id == execution.id }
        updateElementInList(currents, existingIndex, execution)
    }

    override suspend fun addSegment(segment: Segment) = _segments.update { currents ->
        val existingIndex = currents.indexOfFirst { it.id == segment.id }
        updateElementInList(currents, existingIndex, segment)
    }

    override fun getTaskById(taskId: String): Flow<Task?> = tasks.map {
        it.find { task ->
            task.id == taskId
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