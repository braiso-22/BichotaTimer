package com.braiso_22.bichota_timer

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    App(
        darkTheme = isSystemInDarkTheme()
    )
}