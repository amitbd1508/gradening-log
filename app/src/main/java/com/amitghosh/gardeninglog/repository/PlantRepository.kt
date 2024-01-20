package com.amitghosh.gardeninglog.repository

import com.amitghosh.gardeninglog.model.Plant
import com.amitghosh.gardeninglog.repository.database.PlantDatabase
import com.amitghosh.gardeninglog.repository.network.PlantNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlantRepository @Inject constructor(
    private val plantDatabase: PlantDatabase
) {
    val plants = plantDatabase.plantDatabaseDao.getAllPlants()

    suspend fun refreshPlants() {
        withContext(Dispatchers.IO) {
            val plants = async { PlantNetworkDataSource().fetchPlants() }
            plants.await()?.let { plantDatabase.plantDatabaseDao.insert(it) }
        }
    }

    suspend fun insertPlant(plant: Plant) {
        withContext(Dispatchers.IO) {
            plantDatabase.plantDatabaseDao.insertPlant(plant);
        }
    }
}