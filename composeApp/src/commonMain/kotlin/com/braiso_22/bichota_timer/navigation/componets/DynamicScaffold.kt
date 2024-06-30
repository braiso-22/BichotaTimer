package com.braiso_22.bichota_timer.navigation.componets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import bichotatimer.composeapp.generated.resources.Res
import bichotatimer.composeapp.generated.resources.history
import bichotatimer.composeapp.generated.resources.my_day
import com.braiso_22.bichota_timer.tasks.presentation.state.NavigationItem
import org.jetbrains.compose.resources.stringResource

@Composable
fun DynamicScaffold(
    isMobile: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit = {}
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
            items = items,
            modifier = modifier
        ) { padding ->
            content(padding)
        }
    } else {
        DesktopMenu(
            items = items,
            modifier = modifier
        ) { padding ->
            content(padding)
        }
    }
}