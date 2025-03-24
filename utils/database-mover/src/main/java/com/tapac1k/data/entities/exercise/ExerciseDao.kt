package com.tapac1k.data.entities.exercise

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tapac1k.data.entities.TagEntity
import com.tapac1k.domain.entities.Exercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(exerciseEntity: ExerciseEntity)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(exerciseEntity: ExerciseEntity)

    @Query("SELECT * FROM exercise WHERE ex_id = :id ")
    suspend fun getExerciseById(id: Long): List<FullExerciseEntity>

    @Query("SELECT * FROM exercise WHERE archived != 1")
    fun listenExercise(): Flow<List<FullExerciseEntity>>

    @Query("SELECT DISTINCT tag FROM tag")
    fun listenTags(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTag(tagEntity: TagEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTags(tagEntity: List<TagEntity>)

    @Query("UPDATE exercise SET archived = 1 WHERE ex_id = :exerciseId")
    suspend fun archiveExercise(exerciseId: Long)

    @Query("UPDATE tag SET tag = :newName WHERE tag = :tag")
    fun renameTag(tag: String, newName: String)

    @Query("DELETE FROM tag WHERE tagged_exercise_id = :exerciseId")
    suspend fun removeTagsForExercise(exerciseId: Long)
}