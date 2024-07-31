package com.braiso_22.bichota_timer.navigation.componets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.braiso_22.bichota_timer.tasks.presentation.my_day.state.NavigationItem

@Composable
fun NavigationDrawerList(
    selectedItemIndex: Int,
    onClick: (Int) -> Unit,
    items: List<NavigationItem>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
    ) {
        LazyColumn {
            itemsIndexed(items) { index, item ->
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
                        item.badgeCount?.let {
                            Text(it.toString())
                        }
                    }
                )
            }
        }
        VerticalDivider(modifier = Modifier.fillMaxHeight())
    }
}