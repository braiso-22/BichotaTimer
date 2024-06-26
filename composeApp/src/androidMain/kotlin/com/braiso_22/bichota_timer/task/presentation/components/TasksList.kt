package com.braiso_22.bichota_timer.task.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.braiso_22.bichota_timer.tasks.presentation.components.TasksLists
import com.braiso_22.bichota_timer.tasks.presentation.state.TaskUiState

@Preview(showBackground = true)
@Composable
private fun TasksList() {
    TasksLists(
        runningTask = listOf(
            TaskUiState(
                ticket = 1,
                title = "test title",
                isWorkRelated = true,
                duration = "00:10:00"
            )
        ),
        pendingTasks = listOf(
            TaskUiState(
                ticket = 2,
                title = "test title 2",
                isWorkRelated = true,
                duration = "00:10:00"
            ),
            TaskUiState(
                ticket = 2,
                title = "test title 2",
                isWorkRelated = false,
                duration = "00:00:00"
            ),
        ),
        completedTasks = emptyList()
    )
}