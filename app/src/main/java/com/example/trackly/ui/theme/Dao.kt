package com.example.trackly.ui.theme

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao{
    @Insert
    suspend fun addTask(task: TaskEntity)
    @Update
    suspend fun update(task: TaskEntity)

    @Query("SELECT * FROM TASKS")
    fun getTasks(): Flow<List<TaskEntity>>
    @Query("DELETE FROM tasks")
    suspend fun deleteAll()

    @Update
    suspend fun updateTask(task: TaskEntity)
    @Query("UPDATE tasks SET isDone = 0")
    suspend fun resetAllTasks()
    @Delete
    suspend fun deleteTask(task: TaskEntity)

}