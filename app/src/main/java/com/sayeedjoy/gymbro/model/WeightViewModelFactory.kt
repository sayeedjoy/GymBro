package com.sayeedjoy.gymbro.weight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class WeightViewModelFactory(
    private val repo: WeightRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeightViewModel::class.java)) {
            return WeightViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
