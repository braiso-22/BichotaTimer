package com.braiso_22.bichota_timer.tasks.presentation.my_day

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braiso_22.bichota_timer.tasks.domain.entities.Segment
import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import com.braiso_22.bichota_timer.tasks.domain.usecases.GetCategorizedTasks
import com.braiso_22.bichota_timer.tasks.domain.usecases.GetTasksWithExecutionsInDateRange
import com.braiso_22.bichota_timer.tasks.domain.usecases.GetWorkedHoursOfTasks
import com.braiso_22.bichota_timer.tasks.domain.usecases.Ticker
import com.braiso_22.bichota_timer.tasks.domain.usecases.UpsertSegment
import com.braiso_22.bichota_timer.tasks.presentation.my_day.events.MyDayUiEvent
import com.braiso_22.bichota_timer.tasks.presentation.my_day.mappers.toUiState
import com.braiso_22.bichota_timer.tasks.presentation.my_day.state.DayStatsUiState
import com.braiso_22.bichota_timer.tasks.presentation.my_day.state.MyDayUiState
import com.raedghazal.kotlinx_datetime_ext.now
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class MyDayViewModel(
    private val getTasksWithExecutionsInDateRange: GetTasksWithExecutionsInDateRange,
    private val upsertSegment: UpsertSegment,
    private val getWorkedHoursOfTasks: GetWorkedHoursOfTasks,
    private val getCategorizedTasks: GetCategorizedTasks,
    private val ticker: Ticker,
) : ViewModel() {
    private val _state = MutableStateFlow(MyDayUiState())
    val state: StateFlow<MyDayUiState> = _state.asStateFlow()

    private val _tasks = MutableStateFlow(listOf<Task>())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    init {
        viewModelScope.launch {
            getTasksWithExecutionsInDateRange(
                userId = "user1",
                from = LocalDate.now(),
            ).collect { tasks ->
                _tasks.update { tasks }
                updateState()
            }
        }
        viewModelScope.launch {
            ticker.tick().collect {
                updateState()
            }
        }
    }

    fun onEvent(event: MyDayUiEvent) {
        when (event) {
            is MyDayUiEvent.StartTask -> {
                viewModelScope.launch {
                    val task = _tasks.value.find { it.id == event.taskId }
                    task?.let {
                        upsertSegment(Segment(executionId = task.executions.last().id))
                    }
                }
            }

            is MyDayUiEvent.StopTask -> {
                viewModelScope.launch {
                    val task = _tasks.value.find { it.id == event.taskId }
                    task?.let {
                        task.executions.last().segments.find {
                            it.end == null
                        }?.let { segment ->
                            upsertSegment(segment.copy(end = LocalTime.now()))
                        }
                    }
                }
            }
        }
    }

    private fun updateState() {
        val tasks = _tasks.value
        val categorizedTasks = getCategorizedTasks(tasks)
        val workedHours = getWorkedHoursOfTasks(tasks)

        val (completed, running, pending) = categorizedTasks
        _state.update { currentState ->
            currentState.copy(
                stats = DayStatsUiState(
                    hoursWorked = workedHours.toFloat(),
                    // TODO: add the rest of the data
                ),
                completedTasks = completed.map(Task::toUiState),
                inProgressTasks = running.map(Task::toUiState),
                pendingTasks = pending.map(Task::toUiState),
            )
        }

        if (running.isEmpty()) {
            ticker.stop()
        } else {
            ticker.start()
        }
    }
}