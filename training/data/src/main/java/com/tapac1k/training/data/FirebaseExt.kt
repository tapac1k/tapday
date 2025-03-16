package com.tapac1k.training.data

import com.google.firebase.firestore.DocumentSnapshot
import com.tapac1k.training.contract.TrainingTag

fun DocumentSnapshot.readTrainingTag(): TrainingTag {
    return TrainingTag(
        id = id,
        value = getString("value").orEmpty()
    )
}