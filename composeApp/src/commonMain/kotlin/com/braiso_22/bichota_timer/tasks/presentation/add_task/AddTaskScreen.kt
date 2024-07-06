package com.braiso_22.bichota_timer.tasks.presentation.add_task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Work
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import bichotatimer.composeapp.generated.resources.Res
import bichotatimer.composeapp.generated.resources.task_name
import bichotatimer.composeapp.generated.resources.ticket_id
import bichotatimer.composeapp.generated.resources.work_related
import com.braiso_22.bichota_timer.AppTheme
import com.braiso_22.bichota_timer.material_theme.components.switch_with_text.SwitchWithTextComponent
import com.braiso_22.bichota_timer.material_theme.components.switch_with_text.SwitchWithTextUiState
import com.braiso_22.bichota_timer.tasks.presentation.add_task.components.CloseSaveButtonsRowComponent
import com.braiso_22.bichota_timer.tasks.presentation.add_task.events.AddTaskUiEvent
import com.braiso_22.bichota_timer.tasks.presentation.add_task.events.AddTaskViewModelEvents
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AddTaskScreen(
    viewModel: AddTaskViewModel,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(true) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is AddTaskViewModelEvents.OnSave -> {
                    navigateUp()
                }

                is AddTaskViewModelEvents.OnCancel -> {
                    navigateUp()
                }
            }
        }
    }

    AddTaskComponent(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@Composable
fun AddTaskComponent(
    state: AddTaskUiState,
    onEvent: (AddTaskUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            CloseSaveButtonsRowComponent(
                onEvent = onEvent,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = state.name,
                onValueChange = { onEvent(AddTaskUiEvent.NameChanged(it)) },
                label = { Text(stringResource(Res.string.task_name)) },
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = state.ticketId?.toString() ?: "",
                onValueChange = { onEvent(AddTaskUiEvent.TicketIdChanged(it)) },
                label = { Text(stringResource(Res.string.ticket_id)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            SwitchWithTextComponent(
                state = SwitchWithTextUiState(
                    icon = Icons.Outlined.Work,
                    text = stringResource(Res.string.work_related),
                    isChecked = state.isWorkRelated,
                ),
                onCheck = { onEvent(AddTaskUiEvent.IsWorkRelatedChanged(it)) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
private fun AddTaskComponentPreview() {
    AppTheme(
        darkTheme = true,
        dynamicColor = false
    ) {
        AddTaskComponent(
            state = AddTaskUiState(
                id = "",
                name = "",
                isWorkRelated = true,
                ticketId = null,
                userId = ""
            ), onEvent = {},
            modifier = Modifier.fillMaxSize().padding(8.dp)
        )
    }
}

