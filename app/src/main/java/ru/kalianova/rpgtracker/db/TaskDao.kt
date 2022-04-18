/*package ru.kalianova.rpgtracker.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.kalianova.rpgtracker.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Insert
    fun addTask(task: Task)

    @Insert
    fun unsertAll(vararg tasks: Task)

    @Delete
    fun delete(task: Task)
}*/