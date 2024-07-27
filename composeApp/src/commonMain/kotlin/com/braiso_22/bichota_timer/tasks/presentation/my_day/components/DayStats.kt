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
import bichotatimer.composeapp.generated.resources.progress
import bichotatimer.composeapp.generated.resources.start
import bichotatimer.composeapp.generated.resources.estimated_end_of_day
import com.braiso_22.bichota_timer.tasks.presentation.my_day.state.DayStatsUiState
import com.braiso_22.bichota_timer.tasks.presentation.my_day.state.ProgressBarUiState
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt

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
        Header(modifier = Modifier.fillMaxWidth())
        EstimatesRow(
            timeFrom = state.timeFrom,
            timeTo = state.timeTo,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
        ProgressBar(
            state = state.progress,
            modifier = Modifier.fillMaxWidth().padding(8.dp)
        )
    }
}

@Composable
private fun Header(modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(
            text = stringResource(Res.string.status),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(8.dp)
        )
        HorizontalDivider(Modifier.fillMaxWidth())
    }
}

@Composable
private fun EstimatesRow(timeFrom: String, timeTo: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(Res.string.start), style = MaterialTheme.typography.bodySmall)
        Text(timeFrom)
        Text(
            stringResource(Res.string.estimated_end_of_day),
            style = MaterialTheme.typography.bodySmall
        )
        Text(timeTo)
    }
}

@Composable
private fun ProgressBar(state: ProgressBarUiState, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(stringResource(Res.string.progress))
        ProgressBarComponent(
            state = state,
            formatter = { hours ->
                formatTime(hours.toTimeState())
            },
            modifier = Modifier.weight(1f)
        )
    }
}

fun formatTime(state: TimeProgressBarState): String {
    fun padZero(num: Int): String = if (num < 10) "0$num" else num.toString()
    return "${padZero(state.hours)}:${padZero(state.minutes)}:${padZero(state.seconds)}"
}

private fun Float.toTimeState(): TimeProgressBarState {
    val totalSeconds = (this * 3600).roundToInt()
    val h = totalSeconds / 3600
    val m = (totalSeconds % 3600) / 60
    val s = totalSeconds % 60
    return TimeProgressBarState(h, m, s)
}

data class TimeProgressBarState(
    val hours: Int, val minutes: Int, val seconds: Int,
)
