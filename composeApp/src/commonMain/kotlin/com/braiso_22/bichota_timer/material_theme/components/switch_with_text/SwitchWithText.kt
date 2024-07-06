package com.braiso_22.bichota_timer.material_theme.components.switch_with_text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bichotatimer.composeapp.generated.resources.Res
import bichotatimer.composeapp.generated.resources.work_related
import com.braiso_22.bichota_timer.AppTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SwitchWithTextComponent(
    state: SwitchWithTextUiState,
    onCheck: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            state.icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null
                )
                Spacer(Modifier.padding(8.dp))
            }
            Text(stringResource(Res.string.work_related))
        }
        Switch(
            checked = state.isChecked,
            onCheckedChange = onCheck
        )
    }
}

@Preview
@Composable
private fun SwitchWithTextComponentPreview() {
    val state by remember {
        mutableStateOf(
            SwitchWithTextUiState(icon = null, text = "ea", isChecked = false)
        )
    }
    AppTheme(darkTheme = true, dynamicColor = false) {
        SwitchWithTextComponent(
            state = state,
            onCheck = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}