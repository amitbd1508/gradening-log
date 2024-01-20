package com.amitghosh.gradeninglog.repository.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.amitghosh.gradeninglog.getOrAwaitValue
import com.amitghosh.gardeninglog.model.Plant
import com.amitghosh.gardeninglog.repository.database.PlantDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class PlantDatabaseDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: PlantDatabase

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PlantDatabase::class.java
        ).allowMainThreadQueries()
            .build()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertPlant() = runBlockingTest {
        val plant = Plant("test", "2020", 1, "Plant", "twice a month")
        database.plantDatabaseDao.insert(listOf(plant))

        val plants = database.plantDatabaseDao.getAllPlants().getOrAwaitValue()

        assertThat(plants).contains(plant)
    }

    @Test
    fun clearDatabase() = runBlockingTest {
        val plant = Plant("test", "2020", 1, "Plant", "twice a month")
        database.plantDatabaseDao.insert(listOf(plant))
        database.plantDatabaseDao.clear()

        val plants = database.plantDatabaseDao.getAllPlants().getOrAwaitValue()
        assertThat(plants).isEmpty()
    }
}