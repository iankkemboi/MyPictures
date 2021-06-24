package com.ian.payback.image.di

import android.app.Application
import androidx.room.Room
import com.ian.payback.image.data.source.local.AppDatabase
import com.ian.payback.image.data.source.local.dao.ImageInfoDao
import com.ian.payback.image.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            Constants.DB_NAME
        ).allowMainThreadQueries().build()
    }


    @Provides
    internal fun provideImageInfoDao(appDatabase: AppDatabase): ImageInfoDao {
        return appDatabase.imageInfoDao
    }
}