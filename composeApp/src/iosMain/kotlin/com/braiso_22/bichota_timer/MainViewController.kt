package com.braiso_22.bichota_timer

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.window.ComposeUIViewController
import com.braiso_22.bichota_timer.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App(
        isMobile = true,
        darkTheme = isSystemInDarkTheme()
    )
}