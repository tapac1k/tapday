package com.tapac1k.training.data

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.contract.ExerciseGroup
import com.tapac1k.training.contract.TrainingInfo
import com.tapac1k.training.contract.TrainingTag
import com.tapac1k.training.domain.TrainingService
import com.tapac1k.utils.common.resultOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TrainingServiceImpl @Inject constructor(

) : TrainingService {
    private val db = Firebase.firestore
    private val currentUserId = Firebase.auth.currentUser?.uid

    override fun getTrainingTags(): Flow<List<TrainingTag>> {
        return db.collection("users")
            .document(currentUserId!!)
            .collection(TRAINING_TAGS_PATH)
            .snapshots()
            .map {
                it.map { it.readTrainingTag() }
            }
    }

    override suspend fun createTrainingTag(tag: String): Result<TrainingTag> = resultOf {
        var id: String
        val tagName = tag.normalizeString()
        if (isTagExist(tagName, null)) throw TrainingService.TagAlreadyExistException(tagName)
        if (tagName.isBlank()) throw TrainingService.FieldShouldNotBeEmpty("tagName")
        db.collection("users")
            .document(currentUserId!!)
            .collection(TRAINING_TAGS_PATH)
            .document().also {
                id = it.id
            }
            .set(
                mapOf(
                    "value" to tagName.normalizeString()
                )
            )
            .await()
        return@resultOf TrainingTag(id, tagName)
    }

    override suspend fun editTrainingTag(id: String, value: String): Result<Unit> = resultOf {
        val tagName = value.normalizeString()
        if (tagName.isBlank()) throw TrainingService.FieldShouldNotBeEmpty("tagName")
        if (isTagExist(tagName, id)) throw TrainingService.TagAlreadyExistException(value)
        db.collection("users")
            .document(currentUserId!!)
            .collection(TRAINING_TAGS_PATH)
            .document(id)
            .set(
                mapOf(
                    "value" to tagName
                )
            )
            .await()
    }


    override fun getExercises(): Flow<List<Exercise>> {
        return flow {
            emit(
                db.collection("users")
                    .document(currentUserId!!)
                    .collection(TRAINING_TAGS_PATH)
                    .get().await()
                    .associate {
                        it.id to it.readTrainingTag()
                    })
        }.flatMapLatest { tagMap ->
            db.collection("users")
                .document(currentUserId!!)
                .collection(EXERCISES_PATH)
                .snapshots()
                .map {
                    it.map { it.readExercise(tagMap) }
                }
        }
    }

    override suspend fun saveExercise(exercise: Exercise): Result<Exercise> = resultOf {
        val name = exercise.name.normalizeString()
        val id = exercise.id.takeIf { it.isNotBlank() }
        if (name.isBlank()) throw TrainingService.FieldShouldNotBeEmpty("name")
        if (isExerciseExist(name, id)) throw TrainingService.ExerciseAlreadyExistException(exercise.name)
        var result = exercise
        db.collection("users")
            .document(currentUserId!!)
            .collection(EXERCISES_PATH).run {
                if (id != null) {
                    document(id)
                } else {
                    document().also {
                        result = result.copy(id = it.id)
                    }
                }
            }
            .set(
                mapOf(
                    "name" to name,
                    "tags" to exercise.tags.map {
                        db.collection("users")
                            .document(currentUserId)
                            .collection(TRAINING_TAGS_PATH)
                            .document(it.id)
                    },
                    "withWeight" to exercise.withWeight,
                    "timeBased" to exercise.withWeight
                )
            )
            .await()
        return@resultOf result
    }

    override suspend fun getTrainings(date: Long?): Result<List<TrainingInfo>> = resultOf {
        db.collection("users")
            .document(currentUserId!!)
            .collection("trainings")
            .orderBy("date", Query.Direction.DESCENDING)
            .run {
                if (date != null) whereEqualTo("date", date) else this
            }.get()
            .await()
            .map {
                it.readTrainingInfo()
            }
    }

    override suspend fun getExerciseDetails(exerciseId: String): Result<Exercise> = resultOf {
        val tagMap = db.collection("users")
            .document(currentUserId!!)
            .collection(TRAINING_TAGS_PATH)
            .get().await()
            .associate {
                it.id to it.readTrainingTag()
            }
        db.collection("users")
            .document(currentUserId!!)
            .collection(EXERCISES_PATH)
            .document(exerciseId)
            .get()
            .await()
            .readExercise(tagMap)
    }

    override suspend fun saveTraining(
        id: String?,
        exerciseGroups: List<ExerciseGroup>,
        date: Long,
        description: String,
    ): Result<String> = resultOf {
        val trainingId = id ?: db.collection("users")
            .document(currentUserId!!)
            .collection("trainings")
            .document().id
        val trainingRef = db.collection("users")
            .document(currentUserId!!)
            .collection("trainings")
            .document(trainingId)

        // 1. Prefetch existing exerciseGroups
        val existingGroupsSnapshot = trainingRef.collection("exerciseGroups").get().await()
        val existingGroupIds = existingGroupsSnapshot.documents.map { it.id }.toSet()
        val newGroupIds = exerciseGroups.map { it.id }.toSet()

        val groupsToDelete = existingGroupIds - newGroupIds
        val groupsToUpsert = exerciseGroups.associateBy { it.id }

        // 2. Prefetch sets for each existing group (needed for delete diff)
        val existingSetsMap = mutableMapOf<String, Set<String>>() // groupId -> setIds
        existingGroupsSnapshot.documents.forEach { groupDoc ->
            val setsSnapshot = trainingRef.collection("exerciseGroups")
                .document(groupDoc.id)
                .collection("sets")
                .get()
                .await()
            existingSetsMap[groupDoc.id] = setsSnapshot.documents.map { it.id }.toSet()
        }

        // 3. Run the transaction
        db.runTransaction { transaction ->

            // 🔥 Delete groups removed on the client
            groupsToDelete.forEach { groupId ->
                val groupRef = trainingRef.collection("exerciseGroups").document(groupId)
                transaction.delete(groupRef)
            }

            // 🔥 Upsert new or existing groups and their sets
            groupsToUpsert.forEach { (groupId, group) ->
                val groupRef = trainingRef.collection("exerciseGroups").document(groupId)
                transaction.set(
                    groupRef, mapOf(
                        "exerciseRef" to db.collection("users")
                            .document(currentUserId!!)
                            .collection("exercises")
                            .document(group.exercise.id)
                    )
                )

                val existingSetIds = existingSetsMap[groupId] ?: emptySet()
                val newSetIds = group.sets.map { it.id }.toSet()

                val setsToDelete = existingSetIds - newSetIds
                setsToDelete.forEach { setId ->
                    val setRef = groupRef.collection("sets").document(setId)
                    transaction.delete(setRef)
                }

                group.sets.forEach { set ->
                    val setRef = groupRef.collection("sets").document(set.id)
                    transaction.set(
                        setRef, mapOf(
                            "reps" to set.reps,
                            "weight" to set.weight,
                            "time" to set.time
                        )
                    )
                }
            }

            transaction.set(
                trainingRef,
                mapOf(
                    "updatedAt" to System.currentTimeMillis(),
                    "date" to date,
                    "description" to description
                ), SetOptions.merge()
            )
        }.await()
        trainingId
    }

    private fun String.normalizeString() = this.trim().lowercase()


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

    private suspend fun isExerciseExist(name: String, excludeId: String? = null): Boolean {
        db.collection("users")
            .document(currentUserId!!)
            .collection(EXERCISES_PATH)
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

    companion object {
        private const val TRAINING_TAGS_PATH = "training_tags"
        private const val EXERCISES_PATH = "exercises"
    }
}