package com.amitghosh.gardeninglog.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.amitghosh.gardeninglog.repository.PlantRepository
import com.amitghosh.gardeninglog.repository.database.PlantDatabase
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {


    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val dataSource = PlantDatabase.getInstance(applicationContext)
        return try {
            val repository = PlantRepository(dataSource)
            repository.refreshPlants()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }

}