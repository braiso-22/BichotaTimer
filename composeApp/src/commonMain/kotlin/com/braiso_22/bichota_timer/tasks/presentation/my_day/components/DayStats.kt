package com.braiso_22.bichota_timer.tasks.presentation.my_day.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import bichotatimer.composeapp.generated.resources.Res
import bichotatimer.composeapp.generated.resources.status
import bichotatimer.composeapp.generated.resources.process
import bichotatimer.composeapp.generated.resources.start
import bichotatimer.composeapp.generated.resources.estimated_end_of_day
import com.braiso_22.bichota_timer.tasks.presentation.my_day.state.DayStatsUiState
import com.braiso_22.bichota_timer.tasks.presentation.my_day.state.ProgressBarUiState
import org.jetbrains.compose.resources.stringResource

@Composable
fun DayStatsComponent(
    state: DayStatsUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(Res.string.status),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )
        HorizontalDivider(Modifier.fillMaxWidth())

        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(Res.string.start), style = MaterialTheme.typography.bodySmall)
            Text(state.timeFrom)
            Text(
                stringResource(Res.string.estimated_end_of_day),
                style = MaterialTheme.typography.bodySmall
            )
            Text(state.timeTo)
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(Res.string.process))
            ProgressBarComponent(
                state = ProgressBarUiState(
                    progress = state.hoursWorked,
                    total = state.hoursToWork,
                ),
                modifier = Modifier.weight(1f)
            )
        }
    }
}
