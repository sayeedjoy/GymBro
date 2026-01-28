package com.sayeedjoy.gymbro.weight

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WeightViewModel(private val repo: WeightRepository) : ViewModel() {

    val entries: StateFlow<List<WeightEntryEntity>> =
        repo.observeAll()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    fun addEntry(dateEpochDay: Long, weightKg: Double) {
        viewModelScope.launch { repo.add(dateEpochDay, weightKg) }
    }

    fun deleteEntry(id: Long) {
        viewModelScope.launch { repo.delete(id) }
    }
}
