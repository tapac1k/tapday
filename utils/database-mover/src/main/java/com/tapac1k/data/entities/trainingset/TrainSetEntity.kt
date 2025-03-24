package com.tapac1k.data.entities.trainingset

import androidx.room.*
import com.tapac1k.data.entities.exercise.ExerciseEntity
import com.tapac1k.data.entities.exercise.FullExerciseEntity
import com.tapac1k.data.entities.training.TrainingEntity

@Entity(
    tableName = "training_set",
    foreignKeys = [
        ForeignKey(
            entity = ExerciseEntity::class,
            childColumns = ["exercise_id"],
            parentColumns = ["ex_id"]
        ),
        ForeignKey(
            entity = TrainingEntity::class,
            childColumns = ["parent_training_id"],
            parentColumns = ["training_id"]
        )
    ]
)
data class TrainSetEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "set_id") val id: Long = 0L,
    @ColumnInfo(name = "index") val index: Int,
    @ColumnInfo(name = "weight") val weight: Float,
    @ColumnInfo(name = "exercise_id", index = true) val exerciseId: Long,
    @ColumnInfo(name = "parent_training_id", index = true) val trainingId: Long,
    @ColumnInfo(name = "reps") val reps: Int,
    @ColumnInfo(name = "time") val time: Float
)

@DatabaseView(
    "SELECT * FROM training_set LEFT JOIN exercise ON exercise_id = ex_id",
    viewName = "full_set"
)
data class FullTrainSetEntity(
    @Embedded val trainSetEntity: TrainSetEntity,
    @Embedded val exerciseEntity: FullExerciseEntity
)