package com.amitghosh.gardeninglog.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.amitghosh.gardeninglog.model.Plant
import com.amitghosh.gardeninglog.repository.PlantRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantViewModel @Inject constructor(
    val plantRepository: PlantRepository
) : ViewModel() {

    val plants = plantRepository.plants

    private var _darkMode = MutableLiveData<Boolean>()
    val darkMode: LiveData<Boolean>
        get() = _darkMode

    init {
        viewModelScope.launch {
            plantRepository.refreshPlants()
        }
        _darkMode.value = false
    }
    fun insertPlant(plant: Plant) {
        viewModelScope.launch {
            plantRepository.insertPlant(plant)
        }
    }


    fun darkModeChange() {
        _darkMode.value = _darkMode.value != true
    }

    //Navigate to detail
    private val _navigateToPlantDetail = MutableLiveData<Plant>()
    val navigateToPlantDetail
        get() = _navigateToPlantDetail

    fun onPlantClicked(plant: Plant) {
        _navigateToPlantDetail.value = plant
    }

    fun onPlantDetailNavigated() {
        _navigateToPlantDetail.value = null
    }
}