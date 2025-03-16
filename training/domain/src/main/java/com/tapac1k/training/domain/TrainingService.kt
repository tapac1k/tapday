package com.tapac1k.training.domain

import com.tapac1k.training.contract.TrainingTag
import kotlinx.coroutines.flow.Flow

interface TrainingService {
    suspend fun getTrainingTags(): Flow<List<TrainingTag>>
    suspend fun createTrainingTag(tag: String): Result<Unit>
    suspend fun editTrainingTag(id: String, value: String): Result<Unit>

    class TagAlreadyExistException(tagName: String) : Exception("Tag $tagName already exist")
}