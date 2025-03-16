package com.tapac1k.day.data

import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.contract.DayInfo
import com.tapac1k.day.domain.service.DayService
import com.tapac1k.utils.common.resultOf
import kotlinx.coroutines.tasks.await
import readDayInfo
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
    ): Result<Unit> = resultOf {
        val user = Firebase.auth.currentUser!!
        val fiealds = mapOf(
            "updated" to Timestamp.now(),
            "id" to day,
            "mood" to dayActivity.mood,
            "state" to dayActivity.state,
            "sleepHours" to dayActivity.sleepHours,
        )
        val dayFirestore = db.collection("users").document(user.uid).collection(DAYS_PATH).document(day.toString())
        dayFirestore
            .set(fiealds)
            .await()

    }

    override suspend fun getDayInfo(day: Long): Result<DayInfo> = resultOf {
        return@resultOf db.collection("users")
            .document(currentUserId!!)
            .collection(DAYS_PATH)
            .document(day.toString())
            .get()
            .await()
            .readDayInfo()
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

    private fun buildDayList(from: Long, to: Long, map: Map<Long, DayInfo>): List<DayInfo> {
        return (to..from).reversed().map {
            map[it] ?: DayInfo(it, DayActivity(), null)
        }
    }

    private companion object {
        private const val DAYS_PATH = "days"
    }
}