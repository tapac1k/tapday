package com.tapac1k.domain.entities

data class Exercise(
    val id: Long,
    val name: String,
    val withWeight: Boolean,
    val type: Type,
    val tags: List<String>
) {
    enum class Type {
        COUNT, TIME
    }
}