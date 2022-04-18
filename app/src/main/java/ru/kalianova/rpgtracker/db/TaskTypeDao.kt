/*package ru.kalianova.rpgtracker.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.kalianova.rpgtracker.model.TaskType.TaskType

@Dao
interface TaskTypeDao {
    @Query("SELECT * FROM taskType")
    fun getAll(): List<TaskType>

    @Insert
    fun addTask(task: TaskType)
    //fun getTasks(): LiveData<List<TaskType>>

    @Insert
    fun unsertAll(vararg tasks: TaskType)

    @Update
    fun update(task: TaskType)

    @Delete
    fun delete(task: TaskType)
}*/