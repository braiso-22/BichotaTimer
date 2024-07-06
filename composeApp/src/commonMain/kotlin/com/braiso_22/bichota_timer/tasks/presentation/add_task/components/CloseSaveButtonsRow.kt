package com.braiso_22.bichota_timer.tasks.presentation.add_task.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import bichotatimer.composeapp.generated.resources.Res
import bichotatimer.composeapp.generated.resources.cancel
import bichotatimer.composeapp.generated.resources.save
import com.braiso_22.bichota_timer.AppTheme
import com.braiso_22.bichota_timer.tasks.presentation.add_task.events.AddTaskUiEvent
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CloseSaveButtonsRowComponent(
    onEvent: (AddTaskUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { onEvent(AddTaskUiEvent.Cancel) }
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = stringResource(Res.string.cancel)
            )
        }
        Button(onClick = { onEvent(AddTaskUiEvent.Save) }) {
            Text(stringResource(Res.string.save))
        }
    }
}

@Preview
@Composable
private fun CloseSaveButtonsRowComponentPreview() {
    AppTheme(darkTheme = true, dynamicColor = false) {
        CloseSaveButtonsRowComponent(
            onEvent = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}