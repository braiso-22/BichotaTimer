package com.braiso_22.bichota_timer

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Bichota Timer",
    ) {
        App(
            darkTheme = isSystemInDarkTheme()
        )
    }
}