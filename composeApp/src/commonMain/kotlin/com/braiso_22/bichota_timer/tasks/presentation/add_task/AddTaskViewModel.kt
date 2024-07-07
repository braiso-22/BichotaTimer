package com.braiso_22.bichota_timer.tasks.presentation.add_task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.braiso_22.bichota_timer.tasks.domain.entities.Execution
import com.braiso_22.bichota_timer.tasks.domain.entities.Task
import com.braiso_22.bichota_timer.tasks.domain.usecases.AddExecution
import com.braiso_22.bichota_timer.tasks.domain.usecases.UpsertTask
import com.braiso_22.bichota_timer.tasks.presentation.add_task.events.AddTaskUiEvent
import com.braiso_22.bichota_timer.tasks.presentation.add_task.events.AddTaskViewModelEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddTaskViewModel(
    private val upsertTask: UpsertTask,
    private val addExecution: AddExecution,
) : ViewModel() {
    private val _state = MutableStateFlow(AddTaskUiState(userId = "user1"))
    val state: StateFlow<AddTaskUiState> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<AddTaskViewModelEvents>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: AddTaskUiEvent) {
        when (event) {
            is AddTaskUiEvent.IsWorkRelatedChanged -> {
                _state.update {
                    it.copy(isWorkRelated = event.isWorkRelated)
                }
            }

            is AddTaskUiEvent.NameChanged -> {
                _state.update {
                    it.copy(name = event.name)
                }
            }

            is AddTaskUiEvent.TicketIdChanged -> {
                _state.update {
                    it.copy(ticketId = event.ticketId.toIntOrNull())
                }
            }

            is AddTaskUiEvent.Save -> {
                viewModelScope.launch {
                    val task: Task = _state.value.toDomain()
                    val createdTask = upsertTask(task)
                    addExecution(Execution(taskId = createdTask.id))
                    _eventFlow.emit(AddTaskViewModelEvents.OnSave)
                }
            }

            is AddTaskUiEvent.Cancel -> viewModelScope.launch {
                _eventFlow.emit(AddTaskViewModelEvents.OnCancel)
            }
        }
    }
}
