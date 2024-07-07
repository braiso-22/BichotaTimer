package com.braiso_22.bichota_timer.tasks.presentation.my_day

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bichotatimer.composeapp.generated.resources.Res
import bichotatimer.composeapp.generated.resources.add_task
import bichotatimer.composeapp.generated.resources.my_day
import com.braiso_22.bichota_timer.tasks.presentation.my_day.components.DayStatsComponent
import com.braiso_22.bichota_timer.tasks.presentation.my_day.components.TasksLists
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDayScreen(
    openDrawer: () -> Unit,
    addTask: () -> Unit,
    viewModel: MyDayViewModel,
    modifier: Modifier = Modifier,
) {
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.my_day)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = openDrawer
                    ) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = addTask,
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(Modifier.padding(8.dp))
                Text(stringResource(Res.string.add_task))
            }
        }
    ) { padding ->
        Column(modifier = modifier.padding(padding)) {
            DayStatsComponent(
                state = state.stats,
            )
            Spacer(Modifier.padding(16.dp))
            TasksLists(
                runningTask = state.inProgressTasks,
                pendingTasks = state.pendingTasks,
                completedTasks = state.completedTasks,
                onEvent = viewModel::onEvent,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
