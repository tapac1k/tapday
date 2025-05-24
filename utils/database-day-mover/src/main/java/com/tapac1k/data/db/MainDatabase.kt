package com.tapac1k.tapquote.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tapac1k.tapday.data.db.enitites.body.BodyDao
import com.tapac1k.tapday.data.db.enitites.body.BodyParameterEntity
import com.tapac1k.tapday.data.db.enitites.body.BodyParameterRecordEntity
import com.tapac1k.tapday.data.db.enitites.body.BodySummaryEntity
import com.tapac1k.tapday.data.db.enitites.day.DayDao
import com.tapac1k.tapday.data.db.enitites.day.DaySummaryEntity
import com.tapac1k.tapday.data.db.enitites.day.DayParamEntity
import com.tapac1k.tapday.data.db.enitites.day.DayParamRecordEntity
import com.tapac1k.tapday.data.db.enitites.day.EdibleEntity

@Database(
    entities = [
        DaySummaryEntity::class,
        DayParamEntity::class,
        DayParamRecordEntity::class,
        EdibleEntity::class,
        BodySummaryEntity::class,
        BodyParameterEntity::class,
        BodyParameterRecordEntity::class
    ],
    version = 1
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun dayDao(): DayDao
    abstract fun bodyDao(): BodyDao
}