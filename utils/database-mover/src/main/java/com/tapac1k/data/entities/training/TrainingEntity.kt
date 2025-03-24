package com.tapac1k.data.entities.training

import androidx.room.*
import com.tapac1k.data.entities.trainingset.FullTrainSetEntity
import com.tapac1k.data.entities.trainingset.TrainSetEntity

@Entity(tableName = "training")
data class TrainingEntity(
    @PrimaryKey @ColumnInfo(name = "training_id") val id: Long,
    @ColumnInfo(name = "description") val description: String? = null,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "archived") val archived: Boolean = false
)

data class FullTrainingEntity(
    @Embedded val trainingEntity: TrainingEntity,
    @Relation(
        parentColumn = "training_id",
        entityColumn = "parent_training_id"
    )
    val trainingSets: List<FullTrainSetEntity>
)