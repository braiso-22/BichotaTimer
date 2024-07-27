package com.braiso_22.bichota_timer.navigation.componets

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import bichotatimer.composeapp.generated.resources.Res
import bichotatimer.composeapp.generated.resources.app_name
import com.braiso_22.bichota_timer.tasks.presentation.my_day.state.NavigationItem
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun DesktopMenu(
    items: List<NavigationItem>,
    onItemClick: (NavigationItem) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet {
                Text(
                    stringResource(Res.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp),
                )
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
        modifier = modifier
    ) {
        content()
    }
}