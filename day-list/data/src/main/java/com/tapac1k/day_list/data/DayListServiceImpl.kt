package com.tapac1k.day_list.data

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.firestore
import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.contract.DayInfo
import com.tapac1k.day_list.domain.DayListService
import com.tapac1k.firebase_helper.readDayInfo
import com.tapac1k.utils.common.resultOf
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DayListServiceImpl @Inject constructor(

): DayListService {
    private val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

    override suspend fun requestDayList(from: Long, to: Long): Result<List<DayInfo>>  = resultOf{
        return@resultOf suspendCancellableCoroutine<List<DayInfo>> { cont ->
            Log.d(TAG, "requestDayList: from=$from, to=$to")
            val db = Firebase.firestore
            db.collection("users")
                .document(currentUserId!!)
                .collection("days")
                .whereLessThanOrEqualTo("id", from)
                .whereGreaterThanOrEqualTo("id", to)
                .get()
                .addOnSuccessListener {
                    Log.d(TAG, "success")
                    cont.resume(buildDayList(from, to, it.associate { it.id.toLong() to it.readDayInfo() }))
                }
                .addOnFailureListener {
                    Log.d(TAG, "failed", it)
                    cont.resumeWithException(it)
                }
        }
    }

    private fun buildDayList(from: Long, to: Long, map: Map<Long, DayInfo>) :List<DayInfo> {
        return (to..from).reversed().map {
            map[it] ?: DayInfo(it, DayActivity(), null)
        }
    }

    companion object {
        private const val TAG = "DayListServiceImpl"
    }
}