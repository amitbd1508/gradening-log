package com.amitghosh.gardeninglog.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.amitghosh.gardeninglog.repository.database.PlantDatabase
import com.amitghosh.gardeninglog.repository.network.PlantNetworkDataSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = PlantDatabase.getInstance(context)

    @Singleton
    @Provides
    fun providePlantNetworkDataSource() = PlantNetworkDataSource()
}