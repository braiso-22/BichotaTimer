package com.braiso_22.bichota_timer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App(
                isMobile = true,
                darkTheme = isSystemInDarkTheme()
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App(isMobile = false, darkTheme = true)
}