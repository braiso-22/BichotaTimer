package com.braiso_22.bichota_timer.tasks.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import com.braiso_22.bichota_timer.tasks.domain.usecases.GetTasksWithExecutionsInDateRange
import com.braiso_22.bichota_timer.tasks.domain.usecases.GetWorkedHoursOfTasks
import com.braiso_22.bichota_timer.tasks.presentation.state.MyDayUiState
import com.raedghazal.kotlinx_datetime_ext.now
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import com.braiso_22.bichota_timer.tasks.presentation.mappers.toUiState
import com.braiso_22.bichota_timer.tasks.presentation.state.DayStatsUiState

class MyDayViewModel(
    private val getTasksWithExecutionsInDateRange: GetTasksWithExecutionsInDateRange,
    private val getWorkedHoursOfTasks: GetWorkedHoursOfTasks
) : ViewModel() {
    private val _state = MutableStateFlow(MyDayUiState())
    val state: StateFlow<MyDayUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getTasksWithExecutionsInDateRange(
                "user1",
                LocalDate.now(),
            ).collect { tasks ->
                categorizeTasks(tasks)
            }
        }
    }

    private fun categorizeTasks(tasks: List<Task>) {
        val (completed, notCompleted) = tasks.partition { it.isCompleted }
        val (running, pending) = notCompleted.partition { task ->
            task.executions.any { execution ->
                execution.segments.any { segment ->
                    segment.end == null
                }
            }
        }
        _state.update { toUpdate ->
            toUpdate.copy(
                stats = DayStatsUiState(
                    hoursWorked = getWorkedHoursOfTasks(tasks),
                    // TODO: add the rest of the data
                ),
                completedTasks = completed.map(Task::toUiState),
                inProgressTasks = running.map(Task::toUiState),
                pendingTasks = pending.map(Task::toUiState),
            )
        }
    }

    fun onEvent(event: MyDayEvent) {
        // TODO: add events for adding tasks and pause tasks
    }

    sealed class MyDayEvent {

    }
}