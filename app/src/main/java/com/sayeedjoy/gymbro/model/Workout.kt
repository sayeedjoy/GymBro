package com.sayeedjoy.gymbro.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Workout(
    val name: String,
    val sets: String,
    val checked: Boolean = false
    )