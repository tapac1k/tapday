package com.tapac1k.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.tapac1k.data.entities.exercise.ExerciseEntity

@Entity(
    tableName = "tag",
    primaryKeys = ["tag", "tagged_exercise_id"]
)
data class TagEntity(
    @ColumnInfo(name = "tag") val tag: String,
    @ColumnInfo(name = "tagged_exercise_id") val exerciseId: Long
)