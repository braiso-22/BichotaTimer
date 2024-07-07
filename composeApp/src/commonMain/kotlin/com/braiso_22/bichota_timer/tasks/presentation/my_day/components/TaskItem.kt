package com.braiso_22.bichota_timer.tasks.presentation.my_day.components

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
import com.braiso_22.bichota_timer.tasks.presentation.my_day.state.TaskUiState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TaskItem(
    task: TaskUiState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            task.ticket?.let { taskTicket ->
                Text(text = "#${taskTicket}", modifier = Modifier.weight(0.2f))
            }
            Text(
                text = task.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.weight(0.5f)
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

@Preview
@Composable
private fun TaskItemPreview() {
    TaskItem(
        task = TaskUiState(
            id = "1",
            ticket = null,
            title = "",
            isWorkRelated = false,
            duration = "0:00"
        ),
        onClick = {},
        modifier = Modifier
    )
}