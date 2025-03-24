package com.tapac1k.training.data

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.tapac1k.training.contract.Exercise
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

fun DocumentSnapshot.readTrainingInfo(): TrainingInfo {
    return TrainingInfo(
        id = id,
        date = getLong("date")!!,
        desc = getString("description").orEmpty()
    )
}