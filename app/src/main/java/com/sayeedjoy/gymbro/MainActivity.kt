package com.sayeedjoy.gymbro

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.sayeedjoy.gymbro.data.ThemeMode
import com.sayeedjoy.gymbro.data.ThemePreferences
import com.sayeedjoy.gymbro.model.WorkoutViewModel
import com.sayeedjoy.gymbro.model.WorkoutViewModelFactory
import com.sayeedjoy.gymbro.navigation.MainNavGraph
import com.sayeedjoy.gymbro.navigation.BottomNavigationBar
import com.sayeedjoy.gymbro.ui.theme.GymBroTheme
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val themePreferences = remember { ThemePreferences(applicationContext) }
            val themeMode by themePreferences.themeMode.collectAsState(initial = ThemeMode.SYSTEM)
            val scope = rememberCoroutineScope()

            val isDarkTheme = when (themeMode) {
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
                ThemeMode.LIGHT -> false
                ThemeMode.DARK -> true
            }

            GymBroTheme(darkTheme = isDarkTheme) {
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
                        BottomNavigationBar(navController = navController)
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MainNavGraph(
                            navController = navController,
                            viewModel = viewModel,
                            currentThemeMode = themeMode,
                            onThemeModeChange = { newMode ->
                                scope.launch {
                                    themePreferences.setThemeMode(newMode)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
