package com.tapac1k.training.data

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots
import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.contract.ExerciseGroup
import com.tapac1k.training.contract.ShortTrainingInfo
import com.tapac1k.training.contract.TrainingInfo
import com.tapac1k.training.contract.TrainingTag
import com.tapac1k.training.domain.TrainingService
import com.tapac1k.training.domain.usecase.GetExerciseHistoryUseCase
import com.tapac1k.utils.common.resultOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrainingServiceImpl @Inject constructor(

) : TrainingService {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val db = Firebase.firestore
    private val currentUserId = Firebase.auth.currentUser?.uid

    private val tagState = db.collection("users")
        .document(currentUserId!!)
        .collection(TRAINING_TAGS_PATH)
        .snapshots()
        .map {
            it.associate { it.id to it.readTrainingTag() }
        }.stateIn(scope, SharingStarted.Lazily, null)

    private val exercises = db.collection("users")
        .document(currentUserId!!)
        .collection(EXERCISES_PATH)
        .snapshots().combine(tagState.filterNotNull()) { exercisesSnapshot, tagState ->
            exercisesSnapshot.associate { it.id to it.readExercise(tagState) }
        }.stateIn(scope, SharingStarted.Lazily, null)

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
        return exercises.filterNotNull().map {
            it.values.sortedBy { it.name }
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
                    "timeBased" to exercise.timeBased
                )
            )
            .await()
        return@resultOf result
    }

    override suspend fun getTrainings(date: Long?, id: String?): Result<List<ShortTrainingInfo>> = resultOf {
        var query = db.collection("users")
            .document(currentUserId!!)
            .collection("trainings")
            .orderBy("date", Query.Direction.DESCENDING)
            .orderBy(FieldPath.documentId(), Query.Direction.DESCENDING)
            .limit(10)

        if (date != null && id != null) {
            query = query.startAfter(date, id)
        }

        return@resultOf query.get().await().map { trainingDoc ->
            trainingDoc.readShortTrainingInfo(exercises.filterNotNull().first())
        }
    }

    override suspend fun getTraining(id: String): Result<TrainingInfo> = resultOf {
        val query = db.collection("users")
            .document(currentUserId!!)
            .collection("trainings")
            .document(id)

        val exerciseMap = exercises.filterNotNull().first()
        val groups = query.collection("exerciseGroups").get().await().map { groupDoc ->
            groupDoc.readExerciseGroup(exerciseMap)
        }
        return@resultOf query.get().await().readTrainingInfo(groups)
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

    override suspend fun getSetsByExercise(exerciseId: String, historyKey: GetExerciseHistoryUseCase.HistoryKey?): Result<List<Pair<String, ExerciseGroup>>> = resultOf {
        val exerciseMap = exercises.filterNotNull().first()
        var query = db.collectionGroup("exerciseGroups")
            .whereEqualTo(
                "exerciseRef", db.collection("users")
                    .document(currentUserId!!)
                    .collection(EXERCISES_PATH).document(exerciseId)
            )  // exerciseRef = DocumentReference
            .orderBy("date", Query.Direction.DESCENDING)
            .orderBy(FieldPath.documentId(), Query.Direction.DESCENDING)
            .limit(10)
        if (historyKey != null) {
            val after = db.collection("users").document(currentUserId!!).collection("trainings").document(historyKey.afterTrainingId).collection("exerciseGroups").document(historyKey.afterId)
            query = query.startAfter(historyKey.date, after.path)
        }
        query.get()
            .await().map { doc ->
                val pathSegments = doc.reference.path.split("/")
                val trainingIndex = pathSegments.indexOf("trainings")
                val trainingId = pathSegments.getOrNull(trainingIndex + 1)
                trainingId!! to doc.readExerciseGroup(exerciseMap)
            }
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

        val existingGroupsSnapshot = trainingRef.collection("exerciseGroups").get().await()
        val existingGroupIds = existingGroupsSnapshot.documents.map { it.id }.toSet()
        val newGroupIds = exerciseGroups.map { it.id }.toSet()

        val groupsToDelete = existingGroupIds - newGroupIds
        val groupsToUpsert = exerciseGroups.associateBy { it.id }

        val existingSetsMap = mutableMapOf<String, Set<String>>() // groupId -> setIds
        existingGroupsSnapshot.documents.forEach { groupDoc ->
            val setsSnapshot = trainingRef.collection("exerciseGroups")
                .document(groupDoc.id)
                .collection("sets")
                .get()
                .await()
            existingSetsMap[groupDoc.id] = setsSnapshot.documents.map { it.id }.toSet()
        }

        db.runTransaction { transaction ->

            groupsToDelete.forEach { groupId ->
                val groupRef = trainingRef.collection("exerciseGroups").document(groupId)
                transaction.delete(groupRef)
            }

            groupsToUpsert.forEach { (groupId, group) ->
                val groupRef = trainingRef.collection("exerciseGroups").document(groupId)
                transaction.set(
                    groupRef, mapOf(
                        "exerciseRef" to db.collection("users")
                            .document(currentUserId!!)
                            .collection("exercises")
                            .document(group.exercise.id),
                        "date" to group.date,
                        "sets" to group.sets.map {
                            mapOf(
                                "reps" to it.reps,
                                "weight" to it.weight,
                                "time" to it.time
                            )
                        }
                    )
                )
            }

            transaction.set(
                trainingRef,
                mapOf(
                    "updatedAt" to System.currentTimeMillis(),
                    "date" to date,
                    "description" to description,
                    "exerciseSets" to exerciseGroups.map {
                        mapOf(it.exercise.id to it.sets.count())
                    }
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