package com.amitghosh.gardeninglog.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.amitghosh.gardeninglog.model.Plant

@Database(entities = [Plant::class], version = 7, exportSchema = false)
abstract class PlantDatabase : RoomDatabase() {
    abstract val plantDatabaseDao: PlantDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: PlantDatabase? = null

        fun getInstance(context: Context): PlantDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PlantDatabase::class.java,
                        "plantdb"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}