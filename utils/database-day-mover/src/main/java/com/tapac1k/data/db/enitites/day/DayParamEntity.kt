package com.tapac1k.tapday.data.db.enitites.day

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tapac1k.tapday.domain.entities.ParameterType
import com.tapac1k.tapday.domain.entities.day.DayParamInfo

@Entity(
    tableName = "day_param"
)
data class DayParamEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "day_param_id") val id: Long = 0L,
    val title: String,
    val type: ParameterType,
    @ColumnInfo("edited_at") val editedAt: Long = System.currentTimeMillis(),
    @ColumnInfo("deleted_at") val deletedAt: Long = -1L,
)

