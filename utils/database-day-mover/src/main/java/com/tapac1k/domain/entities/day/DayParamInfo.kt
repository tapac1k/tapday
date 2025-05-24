package com.tapac1k.tapday.domain.entities.day

import com.tapac1k.tapday.domain.entities.ParameterType

data class DayParamInfo(
    val id: Long,
    val deletedAt: Long,
    val editedAt: Long,
    val title: String,
    val type: ParameterType
)