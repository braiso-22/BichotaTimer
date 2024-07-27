package com.braiso_22.bichota_timer.navigation.componets

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.braiso_22.bichota_timer.tasks.presentation.my_day.state.NavigationItem
import kotlinx.coroutines.launch

@Composable
fun MobileMenu(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    items: List<NavigationItem>,
    onItemClick: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerList(
                    items = items,
                    selectedItemIndex = selectedItemIndex,
                    onClick = {
                        selectedItemIndex = it
                        onItemClick(items[it])
                        scope.launch {
                            drawerState.close()
                        }
                    }
                )
            }
        },
        drawerState = drawerState,
        modifier = modifier
    ) {
        content()
    }
}