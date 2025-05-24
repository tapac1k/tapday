package com.tapac1k.tapday.data.db.enitites.body

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.tapac1k.tapday.data.db.enitites.day.DayParamRecordEntity
import com.tapac1k.tapday.data.db.enitites.day.DaySummaryEntity

@Entity(
    tableName = "body_summary"
)
data class BodySummaryEntity(
    @PrimaryKey @ColumnInfo(name = "body_summary_id") val id: Long,
    @ColumnInfo("edited_at") val editedAt: Long,
    @ColumnInfo("deleted_at") val deletedAt: Long,
)

data class FullBodySummaryEntity(
    @Embedded
    val summary: BodySummaryEntity,
    @Relation(
        parentColumn = "body_summary_id",
        entityColumn = "body_summary_ref"
    )
    val params: List<BodyParameterRecordEntity>
)