package com.example.trackly.ui.theme

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    companion object{
        @Volatile
        private var Instance: TaskDatabase? = null
        fun getDatabase(context: Context): TaskDatabase{
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase :: class.java,
                    "task_db"
                ).build().also { Instance = it }
            }
        }
    }
}