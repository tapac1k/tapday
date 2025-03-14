package com.tapac1k.day.data

import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.contract.DayInfo
import com.tapac1k.day.domain.DayService
import com.tapac1k.firebase_helper.readDayInfo
import com.tapac1k.utils.common.resultOf
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DayServiceImpl @Inject constructor(

) : DayService {
    val userId = Firebase.auth.currentUser?.uid
    override fun getCurrentDay(): Long {
        return System.currentTimeMillis() / 1000 / 60 / 60 / 24
    }

    override suspend fun saveDayActivity(
        day: Long,
        dayActivity: DayActivity,
    ): Result<Unit> = resultOf {
        return@resultOf suspendCancellableCoroutine<Unit> { cont ->
            val db = Firebase.firestore
            val user = Firebase.auth.currentUser!!
            val fiealds = mapOf(
                "updated" to Timestamp.now(),
                "id" to day,
                "mood" to dayActivity.mood,
                "state" to dayActivity.state,
                "sleepHours" to dayActivity.sleepHours,
            )
            val dayFirestore = db.collection("users").document(user.uid).collection("days").document(day.toString())
            dayFirestore
                .set(fiealds)
                .addOnSuccessListener {
                    cont.resume(Unit)
                }
                .addOnFailureListener {
                    cont.resumeWithException(it)
                }
        }
    }

    override suspend fun getDayInfo(day: Long): Result<DayInfo> = resultOf {
        return@resultOf suspendCancellableCoroutine<DayInfo> { cont ->
            val db = Firebase.firestore
            db.collection("users").document(userId!!).collection("days").document(day.toString()).get()
                .addOnSuccessListener {
                    cont.resume(it.readDayInfo())
                }
                .addOnFailureListener {
                    cont.resumeWithException(it)
                }
        }
    }


}