package com.braiso_22.bichota_timer.tasks.presentation.my_day.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.braiso_22.bichota_timer.tasks.presentation.my_day.state.TaskUiState

fun LazyListScope.tasksWithTitle(
    title: String,
    isExpanded: Boolean,
    onClick: () -> Unit,
    onClickTask: (TaskUiState) -> Unit,
    tasks: List<TaskUiState>,
) {
    item {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(8.dp)
            )
            IconButton(onClick) {
                Icon(
                    imageVector = if (isExpanded)
                        Icons.Default.ExpandLess
                    else
                        Icons.Default.ExpandMore,
                    contentDescription = "Expand"
                )
            }
        }
        HorizontalDivider(Modifier.fillMaxWidth())
    }
    if (isExpanded) {
        if (tasks.isNotEmpty()) {
            items(tasks) { item ->
                TaskItem(
                    task = item,
                    onClick = { onClickTask(item) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            item {
                Text("No items", modifier = Modifier.padding(8.dp))
            }
        }
    }
    item { Spacer(Modifier.padding(16.dp)) }
}