package com.braiso_22.bichota_timer.tasks.presentation.my_day.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import bichotatimer.composeapp.generated.resources.*
import com.braiso_22.bichota_timer.tasks.presentation.my_day.state.TaskUiState
import org.jetbrains.compose.resources.stringResource

@Composable
fun TasksLists(
    runningTask: List<TaskUiState>,
    pendingTasks: List<TaskUiState>,
    completedTasks: List<TaskUiState>,
    modifier: Modifier = Modifier
) {
    val runningTaskTitle = stringResource(Res.string.running_task)
    val pendingTasksTitle = stringResource(Res.string.pending_tasks)
    val completedTasksTitle = stringResource(Res.string.completed_tasks)

    var runningExpanded by remember { mutableStateOf(false) }
    var pendingExpanded by remember { mutableStateOf(false) }
    var completedExpanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier
    ) {
        tasksWithTitle(
            title = runningTaskTitle,
            isExpanded = runningExpanded,
            onClick = { runningExpanded = !runningExpanded },
            tasks = runningTask
        )
        tasksWithTitle(
            title = pendingTasksTitle,
            isExpanded = pendingExpanded,
            onClick = { pendingExpanded = !pendingExpanded },
            tasks = pendingTasks
        )
        tasksWithTitle(
            title = completedTasksTitle,
            isExpanded = completedExpanded,
            onClick = { completedExpanded = !completedExpanded },
            tasks = completedTasks
        )
    }
}