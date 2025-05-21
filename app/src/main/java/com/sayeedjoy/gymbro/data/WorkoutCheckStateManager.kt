package com.sayeedjoy.gymbro.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.checkboxDataStore by preferencesDataStore("checkbox_states")

class WorkoutCheckStateManager(private val context: Context) {

    private val LAST_OPEN_DATE = stringPreferencesKey("last_open_date")

    suspend fun saveCheckedState(name: String, isChecked: Boolean) {
        context.checkboxDataStore.edit { prefs ->
            prefs[booleanPreferencesKey("checked_$name")] = isChecked
        }
    }

    suspend fun loadCheckedStates(): Map<String, Boolean> {
        val prefs = context.checkboxDataStore.data.first()
        return prefs.asMap()
            .filter { it.key.name.startsWith("checked_") && it.value is Boolean }
            .mapKeys { it.key.name.removePrefix("checked_") }
            .mapValues { it.value as Boolean }
    }

    suspend fun saveLastOpenedDate(date: String) {
        context.checkboxDataStore.edit { prefs ->
            prefs[LAST_OPEN_DATE] = date
        }
    }

    suspend fun getLastOpenedDate(): String? {
        return context.checkboxDataStore.data.first()[LAST_OPEN_DATE]
    }

    suspend fun clearAllCheckedStates() {
        context.checkboxDataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
