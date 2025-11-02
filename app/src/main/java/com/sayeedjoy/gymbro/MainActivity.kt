package com.sayeedjoy.gymbro

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.sayeedjoy.gymbro.model.WorkoutViewModel
import com.sayeedjoy.gymbro.model.WorkoutViewModelFactory
import com.sayeedjoy.gymbro.navigation.MainNavGraph
import com.sayeedjoy.gymbro.navigation.BottomNavigationBar
import com.sayeedjoy.gymbro.ui.theme.GymBroTheme

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            GymBroTheme() {
                val view = LocalView.current
                val window = (view.context as ComponentActivity).window
                val backgroundColor = MaterialTheme.colorScheme.background
                val useDarkIcons = backgroundColor.luminance() > 0.5f

                SideEffect {
                    WindowCompat.setDecorFitsSystemWindows(window, false)
                    WindowCompat.getInsetsController(window, view)?.apply {
                        isAppearanceLightStatusBars = useDarkIcons
                        isAppearanceLightNavigationBars = useDarkIcons
                        window.statusBarColor = backgroundColor.toArgb()
                        window.navigationBarColor = backgroundColor.toArgb()
                    }
                }

                val navController = rememberNavController()
                val viewModel: WorkoutViewModel = viewModel(
                    factory = WorkoutViewModelFactory(applicationContext)
                )

                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            navController = navController,
                            currentRoute = navController.currentBackStackEntry?.destination?.route
                        )
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MainNavGraph(
                            navController = navController,
                            viewModel = viewModel,
                            )
                    }
                }

            }
        }
    }
}
