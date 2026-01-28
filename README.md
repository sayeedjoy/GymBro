# GymBro

A modern workout tracking app built with Jetpack Compose for Android. Track your daily exercises, monitor your progress, and stay consistent with your fitness routine.

![GymBro App](https://sayeedjoy.com/wp-content/uploads/2025/05/thumb-scaled.png)

## Features

### Daily Workout Tracking
- View today's exercises based on the current day of the week
- Check off completed sets with interactive checkboxes
- Visual progress ring showing completion percentage
- Celebration message when all exercises are complete
- Automatic daily reset of completion state

### Weekly Schedule
- Browse your complete workout plan organized by day
- Expandable day cards with smooth animations
- Clean, read-only view of all exercises and sets
- Quick overview of active days and total exercises

### Weight Tracking
- Log your body weight over time
- Visual line chart to track progress
- Complete history of all weight entries
- Swipe-to-delete functionality for easy management

### Customizable Appearance
- Light and Dark theme support
- System default option that follows device settings
- Persistent theme preference using DataStore
- Material 3 design with dynamic colors

## Tech Stack

- **UI Framework:** Jetpack Compose
- **Design System:** Material 3
- **Architecture:** MVVM (Model-View-ViewModel)
- **State Management:** StateFlow and Compose State
- **Database:** Room for weight tracking
- **Preferences:** DataStore for theme settings
- **Navigation:** Navigation Compose
- **Async:** Kotlin Coroutines and Flow

## Architecture

The app follows the MVVM architecture pattern:

- **Model:** Data classes and local data sources (`Workout`, `WeightEntry`, Room DAOs)
- **ViewModel:** Business logic and state management (`WorkoutViewModel`, `WeightViewModel`)
- **View:** Composable UI screens (`HomeScreen`, `AllWorkoutScreen`, `WeightScreen`, `SettingsScreen`)

This ensures:
- Clear separation of concerns
- Lifecycle-aware state management
- Reactive UI updates
- Testable code structure

## Getting Started

1. Clone the repository
2. Open the project in Android Studio (Ladybug or newer)
3. Sync Gradle dependencies
4. Run the app on an emulator or physical device

## Requirements

- Android Studio Ladybug or newer
- Minimum SDK: 24 (Android 7.0)
- Target SDK: 35 (Android 15)
- Kotlin 1.9+
