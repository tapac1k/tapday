package com.tapac1k.tapday.domain.entities.body

data class BodyParamInfo(
    val id: Long,
    val title: String,
    val deletedAt: Long,
    val editedAt: Long
)

val mockParamInfo = BodyParamInfo(10L, "123123", 1,1)