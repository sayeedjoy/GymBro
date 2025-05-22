# 🏋️‍♂️ GymBro

**GymBro** is a modern workout tracker app built using **Jetpack Compose**, designed to help users manage their gym sessions with daily routines, dark mode support, and a clean UI. 💪

[![Untitled-design.png](https://i.postimg.cc/vBzvQLnd/Untitled-design.png)](https://postimg.cc/mzP9j9Vd)

## ✨ Features

- 📅 **Today's Workout Screen**
  - Displays your daily exercises based on the current day.
  - Checkboxes to track completed sets.
  - Automatically resets daily completion state.

- 📂 **All Workouts Screen**
  - View all workouts grouped by weekday (e.g. Monday, Saturday).
  - Tap to expand/collapse each day's exercises.
  - Checkbox hidden for read-only clarity.

- ⚙️ **Settings Screen**
  - Toggle between **Light** and **Dark** theme.
  - Fully reactive UI updates.

- 📱 **Material You UI**
  - Built using **Material 3** and Jetpack Compose.
  - Responsive layouts, elevation-less cards, and custom colors.


## 🧱 Tech Stack

- 🧩 Jetpack Compose
- ☀️ Material 3 Theme System
- 🗃️ ViewModel + StateFlow for state management
- 💾 Local storage (WorkoutCheckStateManager) for saving daily progress
- 🧭 Navigation Compose


## 🏗 Architecture

In this app i have used **MVVM (Model-View-ViewModel)** architecture pattern.

- **Model**: Contains workout data structures and local data source (`Workout`, `WorkoutCheckStateManager`, etc.)
- **ViewModel**: Manages UI logic and state (`WorkoutViewModel`, `ThemeViewModel`)
- **View**: Built entirely in Jetpack Compose (`WorkoutScreen`, `AllWorkoutScreen`, `SettingsScreen`)

This architecture ensures:
- Separation of concerns
- Lifecycle-aware state management
- Reactive UI with `StateFlow` and `remember`
