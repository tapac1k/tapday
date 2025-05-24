package com.tapac1k.tapday.data.db.enitites.day

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "day_param_record",
    primaryKeys = ["day_summary_ref", "day_param_ref"],
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = DayParamEntity::class,
            childColumns = ["day_param_ref"],
            parentColumns = ["day_param_id"]
        ),
        androidx.room.ForeignKey(
            entity = DaySummaryEntity::class,
            childColumns = ["day_summary_ref"],
            parentColumns = ["day_summary_id"]
        )
    ],
    indices = [
        Index("day_param_ref"),
        Index("day_summary_ref")
    ]
)
data class DayParamRecordEntity(
    @ColumnInfo(name = "day_summary_ref") val dayId: Long,
    @ColumnInfo(name = "day_param_ref") val parameterId: Long,
    val value: Int
)