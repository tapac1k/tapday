
import com.google.firebase.firestore.DocumentSnapshot
import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.contract.DayInfo
import com.tapac1k.day.domain.models.Habit
import com.tapac1k.day.domain.models.HabitData

fun DocumentSnapshot.readDayInfo(): DayInfo {
    return DayInfo(
        id = getLong("id") ?: 0,
        dayActivity = DayActivity(
            sleepHours = getDouble("sleepHours")?.toFloat() ?: 0f,
            mood = getLong("mood")?.toInt() ?: 0,
            state = getLong("state")?.toInt() ?: 0
        ),
        updated = getTimestamp("updated")?.seconds ?: 0L,
        description = getString("description").orEmpty(),
        positiveSum = getLong("positiveSum")?.toInt() ?: 0,
        negativeSum = getLong("negativeSum")?.toInt() ?: 0,
    )
}


fun DocumentSnapshot.readHabit(): Habit? {
    return Habit(
        id = id,
        name = getString("name") ?: return null,
        isPositive = getBoolean("isPositive") ?: return null
    )
}

fun DocumentSnapshot.readHabitData(): HabitData {
    return HabitData(
        habit = Habit(
            id = getString("id").orEmpty(),
            name = getString("name").orEmpty(),
            isPositive = getBoolean("isPositive") ?: true,
        ),
        state = getLong("state")?.toInt() ?: 1
    )
}