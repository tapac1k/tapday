package com.tapac1k.tapday.domain.entities.day

data class DaySummary(
    val id: Long,
    val editedAt: Long,
    val deletedAt: Long,
    val description: String,
    val happiness: Int?,
    val sleepHours: Float?,
    val extra: String,
    val parameters: List<DayParamRecord>,
    val edibles: List<String>
)

data class ShortDaySummary(
    val id: Long,
    val happiness: Int?,
    val sleepHours: Float?,
    val positiveRate: Int,
    val negativeRate: Int
)

data class DayParamRecord(
    val id: Long,
    val value: Int
)

val mockSummaryPreview = DaySummary(
    10L, 10L, -1L, " asda sd as da sd ", 0, 2.4f, "asdasd", emptyList(), emptyList()
)

val mockShortSummary = ShortDaySummary(
    10L, 54, 2.5f, 4, 2
)