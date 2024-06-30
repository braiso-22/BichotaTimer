package com.braiso_22.bichota_timer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.braiso_22.bichota_timer.navigation.componets.DynamicScaffold
import com.braiso_22.bichota_timer.tasks.presentation.MyDayScreen
import com.braiso_22.bichota_timer.tasks.presentation.MyDayViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
@Preview
fun App(
    isMobile: Boolean,
    darkTheme: Boolean,
) {
    AppTheme(
        darkTheme = darkTheme,
        dynamicColor = true
    ) {
        KoinContext {
            Surface(modifier = Modifier.fillMaxSize()) {
                DynamicScaffold(
                    isMobile = isMobile,
                    modifier = Modifier.fillMaxSize()
                ) {
                    MyDayScreen(
                        viewModel = koinViewModel<MyDayViewModel>(),
                        modifier = Modifier.fillMaxSize().padding(it)
                    )
                }
            }
        }
    }
}