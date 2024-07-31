package com.braiso_22.bichota_timer.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.HourglassBottom
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import bichotatimer.composeapp.generated.resources.Res
import bichotatimer.composeapp.generated.resources.settings
import bichotatimer.composeapp.generated.resources.history
import bichotatimer.composeapp.generated.resources.my_day
import bichotatimer.composeapp.generated.resources.work_hours
import com.braiso_22.bichota_timer.navigation.componets.DesktopMenu
import com.braiso_22.bichota_timer.navigation.componets.MobileMenu
import com.braiso_22.bichota_timer.tasks.presentation.my_day.state.NavigationItem
import org.jetbrains.compose.resources.stringResource

@Composable
fun DynamicScaffold(
    isMobile: Boolean,
    onItemClick: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    content: @Composable () -> Unit = {},
) {
    val items = listOf(
        NavigationItem(
            title = stringResource(Res.string.my_day),
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            destination = "my_day"
        ),
        NavigationItem(
            title = stringResource(Res.string.history),
            selectedIcon = Icons.Filled.History,
            unselectedIcon = Icons.Outlined.History,
            destination = "history"
        ),
        NavigationItem(
            title = stringResource(Res.string.work_hours),
            selectedIcon = Icons.Filled.HourglassBottom,
            unselectedIcon = Icons.Outlined.HourglassBottom,
            destination = "work_hours"
        ),
        NavigationItem(
            title = stringResource(Res.string.settings),
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            destination = "settings"
        ),
    )

    if (isMobile) {
        MobileMenu(
            drawerState = drawerState,
            onItemClick = onItemClick,
            items = items,
            modifier = modifier
        ) {
            content()
        }
    } else {
        DesktopMenu(
            items = items,
            onItemClick = onItemClick,
            modifier = modifier
        ) {
            content()
        }
    }
}