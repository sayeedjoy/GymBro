package com.sayeedjoy.gymbro.weight

import com.sayeedjoy.gymbro.data.WeightDao
import kotlinx.coroutines.flow.Flow

class WeightRepository(private val dao: WeightDao) {
    fun observeAll(): Flow<List<WeightEntryEntity>> = dao.observeAll()

    suspend fun add(dateEpochDay: Long, weightKg: Double) {
        dao.insert(WeightEntryEntity(dateEpochDay = dateEpochDay, weightKg = weightKg))
    }

    suspend fun delete(id: Long) {
        dao.deleteById(id)
    }
}
