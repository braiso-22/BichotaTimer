package com.braiso_22.bichota_timer

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.braiso_22.bichota_timer.di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Bichota Timer",
        ) {
            App(
                isMobile = false,
                darkTheme = isSystemInDarkTheme()
            )
        }
    }
}