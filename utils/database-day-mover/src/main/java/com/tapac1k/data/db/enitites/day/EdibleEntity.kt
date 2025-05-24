package com.tapac1k.tapday.data.db.enitites.day

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "edible",
    primaryKeys = ["edible_name","consumption_day_id"]
)
data class EdibleEntity(
    @ColumnInfo("edible_name") val name: String,
    @ColumnInfo("consumption_day_id") val dayId: Long
)
