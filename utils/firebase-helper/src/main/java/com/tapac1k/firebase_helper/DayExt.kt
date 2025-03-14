package com.tapac1k.firebase_helper

import com.google.firebase.firestore.DocumentSnapshot
import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.contract.DayInfo

fun DocumentSnapshot.readDayInfo(): DayInfo {
    return DayInfo(
        id = getLong("id") ?: 0,
        dayActivity = DayActivity(
            sleepHours = getDouble("sleepHours")?.toFloat() ?: 0f,
            mood = getLong("mood")?.toInt() ?: 0,
            state = getLong("state")?.toInt() ?: 0
        ),
        updated = getTimestamp("updated")?.seconds ?: 0L
    )
}