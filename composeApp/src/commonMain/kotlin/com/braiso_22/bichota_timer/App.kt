package com.braiso_22.bichota_timer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.braiso_22.bichota_timer.navigation.componets.DynamicScaffold
import com.braiso_22.bichota_timer.tasks.presentation.add_task.AddTaskScreen
import com.braiso_22.bichota_timer.tasks.presentation.add_task.AddTaskViewModel
import com.braiso_22.bichota_timer.tasks.presentation.my_day.MyDayScreen
import com.braiso_22.bichota_timer.tasks.presentation.my_day.MyDayViewModel
import kotlinx.coroutines.launch
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
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val coroutineScope = rememberCoroutineScope()
                DynamicScaffold(
                    drawerState = drawerState,
                    isMobile = isMobile,
                    modifier = Modifier.fillMaxSize()
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "my_day") {
                        navigation(
                            route = "my_day",
                            startDestination = "my_day_screen"
                        ) {
                            composable("my_day_screen") {
                                MyDayScreen(
                                    openDrawer = {
                                        coroutineScope.launch {
                                            drawerState.open()
                                        }
                                    },
                                    addTask = {
                                        navController.navigate("add_task")
                                    },
                                    viewModel = koinViewModel<MyDayViewModel>(),
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            composable("add_task") {
                                AddTaskScreen(
                                    viewModel = koinViewModel<AddTaskViewModel>(),
                                    navigateUp = navController::navigateUp,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}