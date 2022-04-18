/*package ru.kalianova.rpgtracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kalianova.rpgtracker.model.Task
import ru.kalianova.rpgtracker.model.TaskType.TaskType

@Database(entities = [Task::class, TaskType::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
    abstract fun taskTypeDao(): TaskTypeDao
}*/