package com.braiso_22.bichota_timer.work_hours_config.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import bichotatimer.composeapp.generated.resources.Res
import bichotatimer.composeapp.generated.resources.days
import com.braiso_22.bichota_timer.work_hours_config.presentation.state.WorkPeriodState
import org.jetbrains.compose.resources.stringResource

@Composable
fun WorkPeriodComponent(
    state: WorkPeriodState,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            modifier = Modifier.size(48.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "${state.hoursPerDay}h",
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Column {
            Text(text = "${state.from} - ${state.to}", fontWeight = FontWeight.Bold)
            Row {
                Text(
                    text = "${state.daysOfPeriod} ${stringResource(Res.string.days)}",
                    fontWeight = FontWeight.Thin
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = state.daysOfWeek, fontWeight = FontWeight.Thin)
            }
        }
        Button(onClick = {}) {
            Icon(
                Icons.Default.Edit,
                contentDescription = null
            )
            Text("Edit")
        }
    }
}