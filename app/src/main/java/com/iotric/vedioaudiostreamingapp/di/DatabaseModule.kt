package com.iotric.vedioaudiostreamingapp.di

import android.app.Application
import androidx.room.Room
import com.alok.basicapps.data.AppDatabase
import com.iotric.vedioaudiostreamingapp.database.migration.MIGRATION_2_1
import com.iotric.vedioaudiostreamingapp.model.ProjectConstants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDb(app: Application): AppDatabase = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .addMigrations(MIGRATION_2_1)
        .build()

    @Singleton
    @Provides
    fun provideEncryptDao(db: AppDatabase) = db.encryptedDao()
}
