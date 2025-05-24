package com.tapac1k.day.data

import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.contract.DayInfo
import com.tapac1k.day.domain.models.FullDayInfo
import com.tapac1k.day.domain.models.Habit
import com.tapac1k.day.domain.models.HabitData
import com.tapac1k.day.domain.service.DayService
import com.tapac1k.utils.common.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import readDayInfo
import readHabit
import readHabitData
import javax.inject.Inject

class DayServiceImpl @Inject constructor(

) : DayService {
    private val db = Firebase.firestore
    private val currentUserId = Firebase.auth.currentUser?.uid
    override fun getCurrentDay(): Long {
        return System.currentTimeMillis() / 1000 / 60 / 60 / 24
    }

    override suspend fun saveDayActivity(
        day: Long,
        dayActivity: DayActivity,
        description: String,
        habitData: List<HabitData>
    ): Result<Unit> = resultOf {
        val user = Firebase.auth.currentUser!!
        val positiveSum = habitData.sumOf { if (it.habit.isPositive) it.state else 0 }
        val negativeSum = habitData.sumOf { if (!it.habit.isPositive) it.state else 0 }
        val fields = mapOf(
            "updated" to Timestamp.now(),
            "id" to day,
            "description" to description,
            "mood" to dayActivity.mood,
            "state" to dayActivity.state,
            "sleepHours" to dayActivity.sleepHours,
            "positiveSum" to positiveSum,
            "negativeSum" to negativeSum,
        )
        val dayFirestore = db.collection("users").document(user.uid).collection(DAYS_PATH).document(day.toString())
        dayFirestore
            .set(fields)
            .await()

        val habitsCollection = dayFirestore.collection(HABITS_PATH)
        val existing = habitsCollection.get().await()
        for (doc in existing.documents) {
            doc.reference.delete()
        }
        for (habit in habitData) {
            habitsCollection.document(habit.habit.id).set(
                mapOf(
                    "id" to habit.habit.id,
                    "state" to habit.state
                )
            ).await()
        }
    }

    override suspend fun getDayInfo(day: Long): Result<FullDayInfo> = resultOf {
        val userRef = db.collection("users").document(currentUserId!!)
        val dayDocRef = userRef.collection(DAYS_PATH).document(day.toString())

        val daySnapshot = dayDocRef.get().await()
        val dayInfo = daySnapshot.readDayInfo()

        val allHabits = userRef.collection(HABITS_PATH).get().await()
            .mapNotNull { it.readHabit() }
            .associateBy { it.id }

        val habitsSnapshot = dayDocRef.collection(HABITS_PATH).get().await()
        val habitDataList = habitsSnapshot.documents.mapNotNull { doc ->
            val habitId = doc.getString("id") ?: return@mapNotNull null
            val state = doc.getLong("state")?.toInt() ?: return@mapNotNull null
            val habit = allHabits[habitId] ?: return@mapNotNull null
            HabitData(habit, state)
        }

        FullDayInfo(
            id = day,
            dayActivity = dayInfo.dayActivity,
            updated = dayInfo.updated,
            description = dayInfo.description,
            positiveSum = dayInfo.positiveSum,
            negativeSum = dayInfo.negativeSum,
            habitsData = habitDataList
        )
    }


    override suspend fun requestDayList(from: Long, to: Long): Result<List<DayInfo>> = resultOf {
        db.collection("users")
            .document(currentUserId!!)
            .collection("days")
            .whereLessThanOrEqualTo("id", from)
            .whereGreaterThanOrEqualTo("id", to)
            .get()
            .await()
            .let {
                buildDayList(from, to, it.associate { it.id.toLong() to it.readDayInfo() })
            }
    }

    override suspend fun saveHabit(habit: Habit) = resultOf {
        val habitToSave = habit.copy(
            name = habit.name.trim().lowercase(),
        )
        val user = Firebase.auth.currentUser!!
        if (habit.name.isBlank()) {
            throw IllegalArgumentException("Habit name cannot be empty")
        }

        val habitId = habit.id.takeIf { it.isNotBlank() } ?: db.collection("users").document(user.uid).collection(HABITS_PATH).document().id
        if (isHabitExist(habit.name, habitId)) {
            throw IllegalArgumentException("Habit with this name already exists")
        }
        val fields = mapOf(
            "name" to habitToSave.name.trim(),
            "isPositive" to habitToSave.isPositive,
        )
        db.collection("users").document(user.uid).collection(HABITS_PATH).document(habitId)
            .set(fields)
            .await()
        habitId
    }

    override suspend fun subscribeAllHabits(): Flow<List<Habit>> {
        return db.collection("users")
            .document(currentUserId!!)
            .collection(HABITS_PATH)
            .snapshots().map {
                it.documents.mapNotNull {
                    it.readHabit()
                }
            }
    }

    private suspend fun isHabitExist(name: String, excludeId: String? = null): Boolean {
        db.collection("users")
            .document(currentUserId!!)
            .collection(HABITS_PATH)
            .whereEqualTo("name", name)
            .get()
            .await()
            .forEach {
                if (it.id != excludeId) {
                    return true
                }
            }
        return false
    }

    private fun buildDayList(from: Long, to: Long, map: Map<Long, DayInfo>): List<DayInfo> {
        return (to..from).reversed().map {
            map[it] ?: DayInfo(it, DayActivity(), null, "", 0, 0)
        }
    }

    private companion object {
        private const val DAYS_PATH = "days"
        private const val HABITS_PATH = "habits"
    }
}