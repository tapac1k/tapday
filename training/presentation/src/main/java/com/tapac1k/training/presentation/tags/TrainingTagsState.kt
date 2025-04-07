package com.tapac1k.training.presentation.tags

import androidx.compose.ui.text.input.TextFieldValue
import com.tapac1k.training.contract.TrainingTag

data class TrainingTagsState(
    val tags: List<TrainingTag> = emptyList(),
    val query: TextFieldValue? = null,
    val loading: Boolean = true
) {
    val filteredTags: List<TrainingTag> = tags.filter { query?.text.isNullOrBlank() || it.value.contains(query!!.text, ignoreCase = true) }
}