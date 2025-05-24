package com.tapac1k.tapday.data.db.enitites.body

import com.tapac1k.tapday.domain.entities.body.BodyParam
import com.tapac1k.tapday.domain.entities.body.BodySummary

fun FullBodySummaryEntity.mapToDomain(): BodySummary{
    return BodySummary(
        summary.id,
        summary.editedAt,
        summary.deletedAt,
        params.map {
            BodyParam(
                it.parameterId,
                it.value
            )
        }
    )
}