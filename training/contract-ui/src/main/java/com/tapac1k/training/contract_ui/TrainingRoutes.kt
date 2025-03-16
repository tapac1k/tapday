package com.tapac1k.training.contract_ui

import com.tapac1k.training.contract.TrainingTag
import kotlinx.serialization.Serializable

@Serializable
data object TrainingTagsRoute

@Serializable
data class TrainingTagRoute(
    val tagId: String? = null,
    val value: String? = null,
)