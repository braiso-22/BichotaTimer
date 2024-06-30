package com.braiso_22.bichota_timer.tasks.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.braiso_22.bichota_timer.tasks.presentation.state.ProgressBarUiState

@Composable
fun ProgressBarComponent(
    state: ProgressBarUiState,
    modifier: Modifier = Modifier
) {
    val madePercentage = if (state.total == 0f) 0.1f else {
        state.progress / state.total * 100
    }
    val remainingPercentage = 100 - madePercentage
    Box(
        modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    horizontal = 8.dp
                )
                .clip(RoundedCornerShape(25))
        ) {
            Box(
                modifier = Modifier.weight(madePercentage)
                    .background(MaterialTheme.colorScheme.primary).padding(16.dp)
            )
            Box(
                modifier = Modifier.weight(remainingPercentage)
                    .background(MaterialTheme.colorScheme.onPrimaryContainer).padding(16.dp)
            )
        }
        Text(
            text = "${state.progress}/${state.total}",
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}
