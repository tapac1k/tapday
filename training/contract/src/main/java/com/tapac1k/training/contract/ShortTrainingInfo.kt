package com.tapac1k.training.contract

data class ShortTrainingInfo(
    val id: String,
    val desc: String,
    val date: Long,
    val exerciseInfo: List<Pair<Exercise, Int>>
) {
    val exerciseCount = exerciseInfo.size
    val totalSets = exerciseInfo.sumOf { it.second }
    val tags = exerciseInfo.flatMap { it.first.tags }.distinct()
}