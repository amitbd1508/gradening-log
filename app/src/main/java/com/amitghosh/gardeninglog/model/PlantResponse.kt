package com.amitghosh.gardeninglog.model

data class PlantResponse (
    val plantList: List<Plant>,
    val totalResults: String,
    val Response: String
)