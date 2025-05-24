package com.tapac1k.tapday.domain.entities.body

import com.tapac1k.tapday.domain.entities.ParameterType

data class BodySummary(
    val id: Long,
    val editedAt: Long,
    val deletedAt: Long,
    val params: List<BodyParam>
)

class BodyParam(
    val id: Long,
    val value: Float
)