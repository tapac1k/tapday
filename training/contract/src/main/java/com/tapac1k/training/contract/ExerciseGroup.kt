package com.tapac1k.training.contract

data class ExerciseGroup(
    val id: String,
    val exercise: Exercise,
    val date: Long,
    val sets: List<ExerciseSet>,
)