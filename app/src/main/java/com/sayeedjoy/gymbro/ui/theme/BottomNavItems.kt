// File: ui.theme/BottomNavItem.kt
package com.sayeedjoy.gymbro.ui.theme

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sayeedjoy.gymbro.R

sealed class BottomNavItem(
    val route: String,
    val label: String,
    val icon: @Composable () -> Unit
) {
    object Home : BottomNavItem(
        "home", "Home",
        { Icon(Icons.Filled.Home, contentDescription = "Home") }
    )

    object AllWorkouts : BottomNavItem(
        "all_workouts", "All Workouts",
        {
            Icon(painterResource(R.drawable.dumbbell),
                modifier = Modifier.size(20.dp),
                contentDescription = "Workouts")
        }
    )

    object Settings : BottomNavItem(
        "settings", "Settings",
        { Icon(Icons.Filled.Settings, contentDescription = "Settings") }
    )
}