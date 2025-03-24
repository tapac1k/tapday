package com.tapac1k.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tapac1k.data.entities.TagEntity
import com.tapac1k.data.entities.exercise.ExerciseDao
import com.tapac1k.data.entities.exercise.ExerciseEntity
import com.tapac1k.data.entities.training.TrainingDao
import com.tapac1k.data.entities.training.TrainingEntity
import com.tapac1k.data.entities.trainingset.FullTrainSetEntity
import com.tapac1k.data.entities.trainingset.TrainSetEntity
import com.tapac1k.data.entities.trainingset.TrainingSetDao

@Database(
    entities = [
        TrainSetEntity::class,
        TrainingEntity::class,
        ExerciseEntity::class,
        TagEntity::class
    ],
    views = [FullTrainSetEntity::class],
    version = 1,
    exportSchema = true
)

@TypeConverters(Converters::class)
abstract class MainDatabase : RoomDatabase() {
    abstract fun trainingSetDao(): TrainingSetDao
    abstract fun trainingDao(): TrainingDao
    abstract fun exerciseDao(): ExerciseDao
}
