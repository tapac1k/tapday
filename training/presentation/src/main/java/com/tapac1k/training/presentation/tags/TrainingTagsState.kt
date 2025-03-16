package com.tapac1k.training.presentation.tags

import com.tapac1k.training.contract.TrainingTag

data class TrainingTagsState(
    val tags: List<TrainingTag> = emptyList(),
    val query: String? = null,
    val loading: Boolean = true
) {
    val filteredTags: List<TrainingTag> = tags.filter { query.isNullOrBlank() || it.value.contains(query, ignoreCase = true) }
}