// File: navigation/NavGraph.kt
package com.sayeedjoy.gymbro.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sayeedjoy.gymbro.model.WorkoutViewModel
import com.sayeedjoy.gymbro.ui.screens.AllWorkoutScreen
import com.sayeedjoy.gymbro.ui.screens.WeightScreen
import com.sayeedjoy.gymbro.ui.screens.WorkoutScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavGraph(
    navController: NavHostController,
    viewModel: WorkoutViewModel, // ✅ pass from MainActivity
) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route
    ) {
        composable(BottomNavItem.Home.route) {
            WorkoutScreen(viewModel = viewModel, navController = navController)
        }
        composable(BottomNavItem.AllWorkouts.route) {
            AllWorkoutScreen(viewModel = viewModel)
        }
        composable(BottomNavItem.Settings.route) {
            WeightScreen()
        }
    }
}

