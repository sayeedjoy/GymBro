package com.sayeedjoy.gymbro.model

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayeedjoy.gymbro.data.WorkoutCheckStateManager
import kotlinx.coroutines.launch
import java.time.LocalDate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.sayeedjoy.gymbro.data.getWorkoutsForToday

@RequiresApi(Build.VERSION_CODES.O)
class WorkoutViewModel(context: Context) : ViewModel() {

    private val stateManager = WorkoutCheckStateManager(context)
    private val workouts = getWorkoutsForToday() // Your local function returning today's list

    var workoutList by mutableStateOf<List<Workout>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            val savedDate = stateManager.getLastOpenedDate()
            val today = LocalDate.now().toString()

            if (savedDate != today) {
                stateManager.clearAllCheckedStates()
                stateManager.saveLastOpenedDate(today)
            }

            val savedStates = stateManager.loadCheckedStates()
            workoutList = workouts.map {
                it.copy(checked = savedStates[it.name] ?: false)
            }
        }
    }
    fun toggleChecked(workout: Workout, isChecked: Boolean) {
        viewModelScope.launch {
            stateManager.saveCheckedState(workout.name, isChecked)
            workoutList = workoutList.map {
                if (it.name == workout.name) it.copy(checked = isChecked) else it
            }
        }
    }

    fun ClearAllCheckedStates() {
        viewModelScope.launch {
            stateManager.clearAllCheckedStates()
            workoutList = workoutList.map { it.copy(checked = false) }
        }
    }
}
