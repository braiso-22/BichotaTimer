package com.braiso_22.bichota_timer.navigation.componets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import bichotatimer.composeapp.generated.resources.Res
import bichotatimer.composeapp.generated.resources.history
import bichotatimer.composeapp.generated.resources.my_day
import com.braiso_22.bichota_timer.tasks.presentation.my_day.state.NavigationItem
import org.jetbrains.compose.resources.stringResource

@Composable
fun DynamicScaffold(
    isMobile: Boolean,
    modifier: Modifier = Modifier,
    drawerState: DrawerState,
    content: @Composable () -> Unit = {}
) {
    val items = listOf(
        NavigationItem(
            title = stringResource(Res.string.my_day),
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        NavigationItem(
            title = stringResource(Res.string.history),
            selectedIcon = Icons.Filled.History,
            unselectedIcon = Icons.Outlined.History,
        ),
    )

    if (isMobile) {
        MobileMenu(
            drawerState = drawerState,
            items = items,
            modifier = modifier
        ) {
            content()
        }
    } else {
        DesktopMenu(
            items = items,
            modifier = modifier
        ) {
            content()
        }
    }
}