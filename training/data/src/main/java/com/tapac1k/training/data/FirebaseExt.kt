package com.tapac1k.training.data

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.getField
import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.contract.ExerciseGroup
import com.tapac1k.training.contract.ExerciseSet
import com.tapac1k.training.contract.ShortTrainingInfo
import com.tapac1k.training.contract.TrainingInfo
import com.tapac1k.training.contract.TrainingTag

fun DocumentSnapshot.readTrainingTag(): TrainingTag {
    return TrainingTag(
        id = id,
        value = getString("value").orEmpty()
    )
}

fun DocumentSnapshot.readExercise(tagMap: Map<String, TrainingTag>): Exercise {
    return Exercise(
        id = id,
        name = getString("name").orEmpty(),
        withWeight = getBoolean("withWeight")!!,
        timeBased = getBoolean("timeBased")!!,
        tags = (get("tags") as List<DocumentReference>).mapNotNull {
            tagMap.get(it.id)
        }
    )
}

fun DocumentSnapshot.readExerciseGroup(exerciseMap: Map<String, Exercise>): ExerciseGroup {
    val exercise = exerciseMap[getField<DocumentReference>("exerciseRef")!!.id]
    val date = getField<Long>("date")!!
    val sets = (get("sets") as? List<Map<String, Any>>).orEmpty().map {
        ExerciseSet(
            id = "",
            reps = (it["reps"] as? Long)?.toInt(),
            weight = (it["weight"] as? Double)?.toFloat(),
            time = (it["time"] as? Long)?.toInt()
        )
    }

    return ExerciseGroup(id, exercise!!, date, sets)
}

fun DocumentSnapshot.readTrainingInfo(exerciseGroups: List<ExerciseGroup>): TrainingInfo {
    return TrainingInfo(
        id = id,
        date = getLong("date")!!,
        desc = getString("description").orEmpty(),
        exerciseGroup = exerciseGroups
    )
}

fun DocumentSnapshot.readShortTrainingInfo(exerciseInfo: Map<String, Exercise>): ShortTrainingInfo {
    val exercise = (get("exerciseSets") as? List<Map<String, Int>>).orEmpty().map {
        it.entries.mapNotNull {  (exerciseInfo.get(it.key) ?: return@mapNotNull null) to it.value }

    }.flatten()

    return ShortTrainingInfo(
        id = id,
        date = getLong("date")!!,
        desc = getString("description").orEmpty(),
        exerciseInfo = exercise
    )
}