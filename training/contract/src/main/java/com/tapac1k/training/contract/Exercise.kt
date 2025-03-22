package com.tapac1k.training.contract

data class Exercise(
    val id: String,
    val name: String,
    val tags: List<TrainingTag>,
    val withWeight: Boolean,
    val timeBased: Boolean
)