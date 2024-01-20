package com.amitghosh.gardeninglog.repository.network

import com.amitghosh.gardeninglog.model.Plant
import com.amitghosh.gardeninglog.model.PlantResponse
import retrofit2.Response
import java.io.IOException

class PlantNetworkDataSource {

    suspend fun fetchPlants(): List<Plant>? {
        var response: Response<PlantResponse>? = null
        var plants: List<Plant>? = null

        try {
            response = MyAPI().getPlants()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        response?.let {
            if (response.isSuccessful) {
                plants = response.body()?.plantList
            }
        }
        return plants
    }
}