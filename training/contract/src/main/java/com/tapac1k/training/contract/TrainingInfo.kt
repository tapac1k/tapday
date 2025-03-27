package com.tapac1k.training.contract

data class TrainingInfo(
    val id: String,
    val desc: String,
    val date: Long,
    val exerciseGroup: List<ExerciseGroup>,
) {
    val exerciseCount = exerciseGroup.size
    val totalSets = exerciseGroup.sumOf { it.sets.size }
    val tags = exerciseGroup.flatMap { it.exercise.tags }.distinct()
}