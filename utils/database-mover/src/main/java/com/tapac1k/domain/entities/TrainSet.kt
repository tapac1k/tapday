package com.tapac1k.domain.entities

data class TrainSet(
    val index: Int,
    val weight: Float,
    val exercise: Exercise,
    val trainingId: Long,
    val reps: Int,
    val time: Float
)