package com.tapac1k.training.contract

data class ExerciseGroup(
    val id: String,
    val exercise: Exercise,
    val sets: List<ExerciseSet>,
)