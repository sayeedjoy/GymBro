package com.sayeedjoy.gymbro.model

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sayeedjoy.gymbro.data.WorkoutCheckStateManager
import com.sayeedjoy.gymbro.data.getWorkoutsForToday
import com.sayeedjoy.gymbro.data.getWeeklyWorkoutSchedule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@RequiresApi(Build.VERSION_CODES.O)
class WorkoutViewModel(context: Context) : ViewModel() {

    private val stateManager = WorkoutCheckStateManager(context)
    private val today = LocalDate.now().toString()

    private val _workoutSchedule = MutableStateFlow<Map<String, List<Workout>>>(emptyMap())
    val workoutSchedule: StateFlow<Map<String, List<Workout>>> = _workoutSchedule.asStateFlow()

    var workoutList by mutableStateOf<List<Workout>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            val savedDate = stateManager.getLastOpenedDate()

            if (savedDate != today) {
                stateManager.clearAllCheckedStates()
                stateManager.saveLastOpenedDate(today)
            }

            val savedStates = stateManager.loadCheckedStates()

            // ✅ Load today's workouts
            val workoutsToday = getWorkoutsForToday()
            workoutList = workoutsToday.map {
                it.copy(checked = savedStates[it.name] ?: false)
            }

            // ✅ Load weekly schedule
            val fullSchedule = getWeeklyWorkoutSchedule() // You must implement this
            _workoutSchedule.value = fullSchedule.mapValues { (_, list) ->
                list.map { it.copy(checked = savedStates[it.name] ?: false) }
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

            // Optionally update the full schedule too
            _workoutSchedule.value = _workoutSchedule.value.mapValues { (_, list) ->
                list.map { it.copy(checked = false) }
            }
        }
    }
}
