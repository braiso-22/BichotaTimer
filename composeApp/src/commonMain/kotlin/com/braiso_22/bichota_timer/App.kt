package com.braiso_22.bichota_timer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import bichotatimer.composeapp.generated.resources.Res
import bichotatimer.composeapp.generated.resources.history
import bichotatimer.composeapp.generated.resources.settings
import com.braiso_22.bichota_timer.navigation.componets.DynamicScaffold
import com.braiso_22.bichota_timer.tasks.presentation.add_task.AddTaskScreen
import com.braiso_22.bichota_timer.tasks.presentation.add_task.AddTaskViewModel
import com.braiso_22.bichota_timer.tasks.presentation.my_day.MyDayScreen
import com.braiso_22.bichota_timer.tasks.presentation.my_day.MyDayViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class, ExperimentalMaterial3Api::class)
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
                val navController = rememberNavController()
                DynamicScaffold(
                    drawerState = drawerState,
                    onItemClick = {
                        if (!navController.popBackStack()) {
                            navController.navigate(it.destination)
                        }
                    },
                    isMobile = isMobile,
                    modifier = Modifier.fillMaxSize()
                ) {
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
                        composable("history") {
                            Scaffold(
                                topBar = {
                                    TopAppBar(
                                        title = { Text(stringResource(Res.string.history)) },
                                        colors = TopAppBarDefaults.topAppBarColors(
                                            containerColor = MaterialTheme.colorScheme.primary,
                                            titleContentColor = MaterialTheme.colorScheme.onPrimary
                                        ),
                                        navigationIcon = {
                                            IconButton(
                                                onClick = {
                                                    coroutineScope.launch {
                                                        drawerState.open()
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    Icons.Default.Menu,
                                                    contentDescription = "Menu",
                                                    tint = MaterialTheme.colorScheme.onPrimary
                                                )
                                            }
                                        }
                                    )
                                },
                            ) {

                            }
                        }
                        composable("settings") {
                            Scaffold(
                                topBar = {
                                    TopAppBar(
                                        title = { Text(stringResource(Res.string.settings)) },
                                        colors = TopAppBarDefaults.topAppBarColors(
                                            containerColor = MaterialTheme.colorScheme.primary,
                                            titleContentColor = MaterialTheme.colorScheme.onPrimary
                                        ),
                                        navigationIcon = {
                                            IconButton(
                                                onClick = {
                                                    coroutineScope.launch {
                                                        drawerState.open()
                                                    }
                                                }
                                            ) {
                                                Icon(
                                                    Icons.Default.Menu,
                                                    contentDescription = "Menu",
                                                    tint = MaterialTheme.colorScheme.onPrimary
                                                )
                                            }
                                        }
                                    )
                                },
                            ) {

                            }
                        }
                    }
                }
            }
        }
    }
}