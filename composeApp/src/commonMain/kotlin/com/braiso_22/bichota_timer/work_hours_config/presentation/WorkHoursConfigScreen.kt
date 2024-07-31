package com.braiso_22.bichota_timer.work_hours_config.presentation


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bichotatimer.composeapp.generated.resources.Res
import bichotatimer.composeapp.generated.resources.no_work_periods_found
import bichotatimer.composeapp.generated.resources.work_hours
import com.braiso_22.bichota_timer.work_hours_config.presentation.components.WorkPeriodComponent
import com.braiso_22.bichota_timer.work_hours_config.presentation.state.WorkPeriodState
import org.jetbrains.compose.resources.stringResource

@Composable
fun WorkHoursConfigScreen(
    openDrawer: () -> Unit,
) {
    WorkHoursConfigScreenComponent(
        periods = List(0) {
            WorkPeriodState(
                hoursPerDay = 8.0f,
                daysOfWeek = "Lunes - Viernes",
                from = "22/03/2023",
                to = "20/07/2023",
                daysOfPeriod = 120
            )
        },
        openDrawer = openDrawer,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkHoursConfigScreenComponent(
    periods: List<WorkPeriodState>,
    openDrawer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.work_hours)) },
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
    ) { padding ->
        LazyColumn(
            modifier = modifier.padding(padding),
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Work periods",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                if(periods.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(stringResource(Res.string.no_work_periods_found))
                    }
                }
            }

            items(periods) { period ->
                WorkPeriodComponent(
                    state = period,
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                )
            }
        }
    }
}