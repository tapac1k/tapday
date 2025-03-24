package com.tapac1k.data.entities.exercise

import androidx.room.*
import com.tapac1k.data.entities.TagEntity
import com.tapac1k.data.entities.training.TrainingEntity
import com.tapac1k.data.entities.trainingset.FullTrainSetEntity
import com.tapac1k.domain.entities.Exercise

@Entity(tableName = "exercise")
data class ExerciseEntity(
    @PrimaryKey @ColumnInfo(name = "ex_id") val id: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "with_weight") val withWeight: Boolean,
    @ColumnInfo(name = "type") val type: Exercise.Type,
    @ColumnInfo(name = "archived") val archived: Boolean = false
)

data class FullExerciseEntity(
    @Embedded val exerciseEntity: ExerciseEntity,
    @Relation(
        parentColumn = "ex_id",
        entityColumn = "tagged_exercise_id"
    )
    val tags: List<TagEntity>
)