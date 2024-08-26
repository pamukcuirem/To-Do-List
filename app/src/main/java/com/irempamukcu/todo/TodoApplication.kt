package com.irempamukcu.todo

import android.app.Application

class TodoApplication : Application() {

    // Lazy initialization of the database and repository
    val database by lazy { TaskItemDatabase.getDatabase(this) }
    val repository by lazy { TaskItemRepository(database.taskItemDao()) }
}
