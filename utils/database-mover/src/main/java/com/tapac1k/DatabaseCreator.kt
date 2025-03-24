package com.tapac1k

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.room.Room
import com.tapac1k.data.DatabaseRepositoryImpl
import com.tapac1k.data.MainDatabase
import com.tapac1k.data.mappers.ExerciseMapper
import com.tapac1k.data.mappers.TrainingMapper
import com.tapac1k.data.mappers.TrainingSetMapper
import com.tapac1k.domain.DatabaseRepository
import java.util.concurrent.Executors

fun getDatabaseRepository(context: Context) : DatabaseRepository {
    val database = Room.databaseBuilder(context, MainDatabase::class.java, "workout_db.db")
        .setTransactionExecutor(Executors.newSingleThreadExecutor())
        .build()

    return DatabaseRepositoryImpl(database, TrainingMapper(), TrainingSetMapper(),ExerciseMapper())
}