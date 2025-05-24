package com.tapac1k.tapday.data.db.enitites.body

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "body_param"
)
data class BodyParameterEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "body_param_id") val id: Long = 0L,
    val title: String,
    @ColumnInfo("edited_at") val editedAt: Long = System.currentTimeMillis(),
    @ColumnInfo("deleted_at") val deletedAt: Long = -1L,
)