package com.seweryn.cvapp.di

import android.app.Application
import androidx.room.Room
import com.seweryn.cvapp.data.CVRepository
import com.seweryn.cvapp.data.CVRepositoryImpl
import com.seweryn.cvapp.data.Constants
import com.seweryn.cvapp.data.local.Database
import com.seweryn.cvapp.data.remote.CVApi
import com.seweryn.cvapp.utils.SchedulerProvider
import com.seweryn.cvapp.utils.SchedulerProviderImpl
import com.seweryn.cvapp.utils.network.ConnectionManager
import com.seweryn.cvapp.utils.network.ConnectionManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideConnectionManager(): ConnectionManager = ConnectionManagerImpl(application)

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = SchedulerProviderImpl()

    @Provides
    @Singleton
    fun provideCVRepository(apiInterface: CVApi, database: Database): CVRepository =
        CVRepositoryImpl(apiInterface, database)

    @Provides
    @Singleton
    fun provideEventsDatabase(): Database = Room.databaseBuilder(
        application,
        Database::class.java, Constants.CV_DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()
}