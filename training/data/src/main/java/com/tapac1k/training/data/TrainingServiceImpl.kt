package com.tapac1k.training.data

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.tapac1k.training.contract.TrainingTag
import com.tapac1k.training.domain.TrainingService
import com.tapac1k.utils.common.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TrainingServiceImpl @Inject constructor(

) : TrainingService {
    private val db = Firebase.firestore
    private val currentUserId = Firebase.auth.currentUser?.uid

    override suspend fun getTrainingTags(): Flow<List<TrainingTag>> {
        return db.collection("users")
            .document(currentUserId!!)
            .collection(TRAINING_TAGS_PATH)
            .snapshots()
            .map {
                it.map { it.readTrainingTag() }
            }
    }

    override suspend fun createTrainingTag(tag: String): Result<Unit> = resultOf {
        if (isTagExist(tag, null)) throw TrainingService.TagAlreadyExistException(tag)
        db.collection("users")
            .document(currentUserId!!)
            .collection(TRAINING_TAGS_PATH)
            .document()
            .set(
                mapOf(
                    "value" to tag.normalizeTag()
                )
            )
            .await()
    }

    override suspend fun editTrainingTag(id: String, value: String): Result<Unit> = resultOf {
        if (isTagExist(value, id)) throw TrainingService.TagAlreadyExistException(value)
        db.collection("users")
            .document(currentUserId!!)
            .collection(TRAINING_TAGS_PATH)
            .document(id)
            .set(
                mapOf(
                    "value" to value.normalizeTag()
                )
            )
            .await()
    }

    private suspend fun isTagExist(tagName: String, excludeId: String? = null): Boolean {
        db.collection("users")
            .document(currentUserId!!)
            .collection(TRAINING_TAGS_PATH)
            .whereEqualTo("value", tagName)
            .get()
            .await()
            .forEach {
                if (it.id != excludeId) {
                    return true
                }
            }

        return false
    }

    private fun String.normalizeTag() = this.trim().lowercase()

    companion object {
        private const val TRAINING_TAGS_PATH = "training_tags"
    }
}