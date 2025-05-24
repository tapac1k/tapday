package com.tapac1k.tapday.data.db.enitites.day

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.tapac1k.tapday.domain.entities.ParameterType

@Entity(
    tableName = "day_summary"
)
data class DaySummaryEntity(
    @PrimaryKey @ColumnInfo("day_summary_id") val id: Long,
    @ColumnInfo("edited_at") val editedAt: Long,
    @ColumnInfo("deleted_at") val deletedAt: Long = -1L,
    val description: String = "",
    val happiness: Int? = null,
    val sleepHours: Float? = null,
    val extra: String = ""
)

data class ListDaySummaryEntity(
    @PrimaryKey @ColumnInfo("day_summary_id") val id: Long,
    @ColumnInfo("edited_at") val editedAt: Long,
    @ColumnInfo("deleted_at") val deletedAt: Long = -1L,
    val description: String = "",
    val happiness: Int? = null,
    val sleepHours: Float? = null,
    val extra: String = "",
    val sumList: List<SumInfo>
)

data class SumInfo(
    val overall: Int,
    val type: ParameterType
)

data class FullDaySummary(
    @Embedded
    val summary: DaySummaryEntity,
    @Relation(
        parentColumn = "day_summary_id",
        entityColumn = "day_summary_ref"
    )
    val params: List<DayParamRecordEntity>,

    @Relation(
        parentColumn = "day_summary_id",
        entityColumn = "consumption_day_id"
    )
    val edibles: List<EdibleEntity>
)