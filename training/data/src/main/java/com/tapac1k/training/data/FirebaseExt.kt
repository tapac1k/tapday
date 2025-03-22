package com.tapac1k.training.data

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.contract.TrainingTag
import kotlinx.coroutines.tasks.await

fun DocumentSnapshot.readTrainingTag(): TrainingTag {
    return TrainingTag(
        id = id,
        value = getString("value").orEmpty()
    )
}

suspend fun DocumentSnapshot.readExercise(): Exercise {
    return Exercise(
        id = id,

        name = getString("name").orEmpty(),
        withWeight = getBoolean("withWeight")!!,
        timeBased = getBoolean("timeBased")!!,
        tags = (get("tags") as List<DocumentReference>).map {
            it.get().await().readTrainingTag()
        }
    )
}