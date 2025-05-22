package com.sayeedjoy.gymbro.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentRoute: String?
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.AllWorkouts,
        BottomNavItem.Settings
    )

    NavigationBar(
        tonalElevation = 6.dp,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route)
                    }
                },
                icon = { item.icon() },
                label = { Text(item.label) }
            )
        }
    }
}
