package com.tapac1k.tapday.domain.entities

enum class ParameterType {
    GOOD_HABIT_BOOLEAN,
    BAD_HABIT_BOOLEAN,
    GOOD_STABLE_HABIT,
    BAD_STABLE_HABIT;

    fun isStable(): Boolean {
        return this == GOOD_STABLE_HABIT || this == BAD_STABLE_HABIT
    }

    fun isGood(): Boolean {
        return this == GOOD_STABLE_HABIT || this == GOOD_HABIT_BOOLEAN
    }
}

fun getParamType(stepped: Boolean, postive: Boolean): ParameterType {
   return when (Pair(stepped, postive)) {
        Pair(false, false) -> ParameterType.BAD_HABIT_BOOLEAN
        Pair(false, true) -> ParameterType.GOOD_HABIT_BOOLEAN
        Pair(true, false) -> ParameterType.BAD_STABLE_HABIT
        else /*tru, true*/ -> ParameterType.GOOD_STABLE_HABIT
    }
}