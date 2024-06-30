package com.braiso_22.bichota_timer.tasks.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.braiso_22.bichota_timer.tasks.presentation.state.TaskUiState

@Composable
fun TaskItem(
    task: TaskUiState,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Text(text = "#${task.ticket}", modifier = Modifier.weight(0.1f))
            Text(
                text = task.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.weight(0.6f)
            )
            Text(
                text = task.duration,
                modifier = Modifier.weight(0.2f),
                textAlign = TextAlign.Center
            )
            Icon(
                imageVector = if (task.isWorkRelated)
                    Icons.Default.Work
                else
                    Icons.Default.SelfImprovement,
                contentDescription = "Is work related",
                modifier = Modifier.weight(0.1f),
            )
        }
    }
}