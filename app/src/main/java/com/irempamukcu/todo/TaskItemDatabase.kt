package com.irempamukcu.todo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TaskItem::class], version = 1, exportSchema = false)
abstract class TaskItemDatabase : RoomDatabase() {

    abstract fun taskItemDao(): TaskItemDao

    companion object {
        @Volatile
        private var INSTANCE: TaskItemDatabase? = null

        fun getDatabase(context: Context): TaskItemDatabase {
            // Double-checked locking to ensure that the database is created only once
            return INSTANCE ?: synchronized(this) {
                val instance = INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
                instance
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TaskItemDatabase::class.java, "task_item_database"
            ).build()
    }
}
