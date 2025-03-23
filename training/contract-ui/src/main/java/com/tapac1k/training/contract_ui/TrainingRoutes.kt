package com.tapac1k.training.contract_ui

import kotlinx.serialization.Serializable

@Serializable
data object TrainingTagsRoute

@Serializable
data class TrainingTagRoute(
    val tagId: String? = null,
    val value: String? = null,
)

@Serializable
data class ExerciseListRoute(
    val query: String? = null,
    val tags: List<String> = emptyList(),
)

@Serializable
data class ExerciseSelectionRoute(
    val query: String? = null,
    val tags: List<String> = emptyList(),
)

@Serializable
data class ExerciseDetailsRoute(
    val exerciseId: String? = null,
)

@Serializable
data object TrainingListRoute

@Serializable
data class TrainingDetailsRoute(val id: String? = null)