package com.braiso_22.bichota_timer.navigation.componets

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.braiso_22.bichota_timer.tasks.presentation.state.NavigationItem

@Composable
fun NavigationDrawerList(
    selectedItemIndex: Int,
    onClick: (Int) -> Unit,
    items: List<NavigationItem>
) {
    items.forEachIndexed { index, item ->
        NavigationDrawerItem(
            label = { Text(item.title) },
            selected = index == selectedItemIndex,
            onClick = { onClick(index) },
            icon = {
                Icon(
                    imageVector = if (index == selectedItemIndex)
                        item.selectedIcon
                    else
                        item.unselectedIcon,
                    contentDescription = null
                )
            },
            badge = {
                item.badgeCount?.let{
                    Text(it.toString())
                }
            }
        )
    }
}