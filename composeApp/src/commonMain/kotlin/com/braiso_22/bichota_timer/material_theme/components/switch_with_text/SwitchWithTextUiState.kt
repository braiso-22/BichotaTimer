package com.braiso_22.bichota_timer.material_theme.components.switch_with_text

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector

@Stable
data class SwitchWithTextUiState(
    val icon: ImageVector?,
    val text: String,
    val isChecked: Boolean,
)
