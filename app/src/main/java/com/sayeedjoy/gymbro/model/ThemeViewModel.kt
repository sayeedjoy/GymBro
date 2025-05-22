// ThemeViewModel.kt
package com.sayeedjoy.gymbro.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeViewModel : ViewModel() {
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode = _isDarkMode.asStateFlow()

    fun toggleTheme() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun setDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
    }
}
