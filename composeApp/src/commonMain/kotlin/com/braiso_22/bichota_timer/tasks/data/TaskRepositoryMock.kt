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
        setIdIfEmpty: (T, List<T>) -> T,
    ): List<T> {
        val index = items.indexOfFirst(getItemIndex)

        val updatedElement = if (index != -1) {
            newItem
        } else {
            setIdIfEmpty(newItem, items)
        }

        return if (index != -1) {
            items.toMutableList().apply {
                set(index, updatedElement)
            }
        } else {
            items + updatedElement
        }
    }


    override suspend fun addTask(task: Task): Task {
        var taskWithId = task
        _tasks.update { currents ->
            val newList = upsertItemInList(
                items = currents,
                getItemIndex = { taskWithId.id == it.id },
                newItem = taskWithId,
                setIdIfEmpty = { element, list ->
                    val newId = list.size + 1
                    element.copy(id = "$newId").also {
                        taskWithId = it
                    }
                }
            )
            newList
        }
        val newTask = _tasks.value.first { it.id == taskWithId.id }
        return newTask
    }


    override suspend fun addExecution(execution: Execution): Execution {
        var executionWithId = execution
        _executions.update { currents ->
            upsertItemInList(
                items = currents,
                getItemIndex = { it.id == executionWithId.id },
                newItem = executionWithId,
                setIdIfEmpty = { element, list ->
                    val newId = list.size + 1
                    element.copy(id = "$newId").also {
                        executionWithId = it
                    }
                }
            )
        }
        return _executions.value.first { it.id == executionWithId.id }
    }

    override suspend fun addSegment(segment: Segment): Segment {
        var segmentWithId = segment
        _segments.update { currents ->
            upsertItemInList(
                items = currents,
                getItemIndex = { it.id == segmentWithId.id },
                newItem = segmentWithId,
                setIdIfEmpty = { element, list ->
                    val newId = list.size + 1
                    element.copy(id = "$newId").also {
                        segmentWithId = it
                    }
                }
            )
        }
        return _segments.value.first { it.id == segmentWithId.id }
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