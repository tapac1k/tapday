package com.tapac1k.tapday

import android.content.Context
import androidx.room.Room
import com.tapac1k.tapday.data.DatabaseRepositoryImpl
import com.tapac1k.tapday.domain.DatabaseRepository
import com.tapac1k.tapquote.data.db.MainDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindAnalyticsService(
        databaseRepositoryImpl: DatabaseRepositoryImpl
    ): DatabaseRepository


}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        MainDatabase::class.java,
        "main_db.db"
    ).build()
}