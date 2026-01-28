// File: navigation/NavGraph.kt
package com.sayeedjoy.gymbro.navigation


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sayeedjoy.gymbro.model.WorkoutViewModel
import com.sayeedjoy.gymbro.ui.screens.AddWeightScreen
import com.sayeedjoy.gymbro.ui.screens.AllWorkoutScreen
import com.sayeedjoy.gymbro.ui.screens.WeightScreen
import com.sayeedjoy.gymbro.ui.screens.WorkoutScreen
import com.sayeedjoy.gymbro.weight.GymBroDatabase
import com.sayeedjoy.gymbro.weight.WeightRepository
import com.sayeedjoy.gymbro.weight.WeightViewModel
import com.sayeedjoy.gymbro.weight.WeightViewModelFactory
import okhttp3.Route

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavGraph(
    navController: NavHostController,
    viewModel: WorkoutViewModel,
) {

    val context = LocalContext.current
    val db = GymBroDatabase.get(context)
    val repo = WeightRepository(db.weightDao())
    val factory = WeightViewModelFactory(repo)

    val weightVm: WeightViewModel = viewModel(factory = factory)
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
            WeightScreen(
                vm = weightVm,
                onAddClick = { navController.navigate(Routes.WEIGHT_ADD) }
            )
        }
        composable(Routes.WEIGHT_ADD){
            AddWeightScreen(
                onSave = {dateEpochDay, weightKg -> weightVm.addEntry(dateEpochDay, weightKg)
                    navController.popBackStack()
                },
                onCancel = {navController.popBackStack() }
            )
        }
    }
}

