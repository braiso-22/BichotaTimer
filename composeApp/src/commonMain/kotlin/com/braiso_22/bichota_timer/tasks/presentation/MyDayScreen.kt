package com.braiso_22.bichota_timer.tasks.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.braiso_22.bichota_timer.tasks.presentation.components.DayStatsComponent
import com.braiso_22.bichota_timer.tasks.presentation.components.TasksLists
import com.braiso_22.bichota_timer.tasks.presentation.state.DayStatsUiState
import com.braiso_22.bichota_timer.tasks.presentation.state.MyDayUiState

@Composable
fun MyDayScreen(
    viewModel: MyDayViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(Modifier.padding(8.dp))
                Text("Add task")
            }
        }
    ) {
        Column(modifier = modifier) {
            DayStatsComponent(
                state = state.stats,
            )
            Spacer(Modifier.padding(16.dp))
            TasksLists(
                runningTask = state.inProgressTasks,
                pendingTasks = state.pendingTasks,
                completedTasks = state.completedTasks,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
