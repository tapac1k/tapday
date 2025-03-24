package com.tapac1k.data

import androidx.room.TypeConverter
import com.tapac1k.domain.entities.Exercise

class Converters {
    @TypeConverter
    fun typeFromInt(value: Int): Exercise.Type {
        return Exercise.Type.values().get(value)
    }

    @TypeConverter
    fun toType(value: Exercise.Type ): Int {
        return value.ordinal
    }
}
