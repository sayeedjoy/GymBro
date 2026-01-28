package com.sayeedjoy.gymbro.weight

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weight_entries")
data class WeightEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    // Store date as epochDay (LocalDate.toEpochDay())
    val dateEpochDay: Long,
    val weightKg: Double
)