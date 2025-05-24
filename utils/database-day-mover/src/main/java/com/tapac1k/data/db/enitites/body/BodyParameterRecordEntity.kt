package com.tapac1k.tapday.data.db.enitites.body

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "body_record",
    primaryKeys = ["body_summary_ref", "body_param_ref"],
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = BodyParameterEntity::class,
            childColumns = ["body_param_ref"],
            parentColumns = ["body_param_id"]
        ),
        androidx.room.ForeignKey(
            entity = BodySummaryEntity::class,
            childColumns = ["body_summary_ref"],
            parentColumns = ["body_summary_id"]
        )
    ],
    indices = [
        Index("body_param_ref"),
        Index("body_summary_ref")
    ]
)
data class BodyParameterRecordEntity(
    @ColumnInfo("body_summary_ref") val summaryId: Long,
    @ColumnInfo("body_param_ref") val parameterId: Long,
    val value: Float
)