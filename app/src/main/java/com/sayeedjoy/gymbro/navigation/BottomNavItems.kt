package com.sayeedjoy.gymbro.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
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
        "all_workouts", "Workouts",
        {
            Icon(painterResource(R.drawable.dumbbell),
                modifier = Modifier.size(20.dp),
                contentDescription = "Workouts")
        }
    )

    object Weight : BottomNavItem(
        "weight", "Weight",
        {
            Icon(painterResource(R.drawable.fitness_center_24px),
                modifier = Modifier.size(20.dp),
                contentDescription = "Weight")
        }
    )
}