package com.tapac1k.domain.entities

data class Training(
    val id: Long,
    val description: String? = null,
    val date: Long,
    val sets: List<TrainSet>
) {
    fun getTags(): List<String> {
        return sets.flatMap { it.exercise.tags }.toSet().toList()
    }
}