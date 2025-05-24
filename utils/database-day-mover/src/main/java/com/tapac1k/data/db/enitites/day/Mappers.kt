package com.tapac1k.tapday.data.db.enitites.day

import com.tapac1k.tapday.domain.entities.day.DayParamRecord
import com.tapac1k.tapday.domain.entities.day.DayParamInfo
import com.tapac1k.tapday.domain.entities.day.DaySummary

fun List<FullDaySummary>.mapToDomain(): List<DaySummary> {
    return map { it.mapToDomain()  }
}

fun FullDaySummary.mapToDomain(): DaySummary {
    return let { it ->
        val params = it.params.map {
            DayParamRecord(
                id = it.parameterId,
                value = it.value
            )
        }
        val edibles = it.edibles.map { it.name }
        DaySummary(
            id = it.summary.id,
            editedAt = it.summary.editedAt,
            deletedAt = it.summary.deletedAt,
            description = it.summary.description,
            happiness = it.summary.happiness,
            sleepHours = it.summary.sleepHours,
            extra = it.summary.extra,
            parameters = params,
            edibles = edibles
        )
    }
}

fun DayParamEntity.mapToDomain(): DayParamInfo {
    return DayParamInfo(
        id = id,
        deletedAt = deletedAt,
        editedAt = editedAt,
        title = title,
        type = type
    )
}

fun DaySummary.mapToEntity(): DaySummaryEntity {
    return DaySummaryEntity(
        id = id,
        editedAt = editedAt,
        deletedAt = deletedAt,
        description = description,
        happiness = happiness,
        sleepHours = sleepHours,
        extra = extra
    )
}

fun DayParamRecord.mapToEntity(summaryId: Long): DayParamRecordEntity {
    return DayParamRecordEntity(
        dayId = summaryId, parameterId = id, value = value
    )
}