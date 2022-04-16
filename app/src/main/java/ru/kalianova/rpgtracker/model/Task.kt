package ru.kalianova.rpgtracker.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.kalianova.rpgtracker.model.TaskType.TaskType
import java.util.*

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: Int?,
    @ColumnInfo(name = "difficulty") val difficulty: Int,
    @ColumnInfo(name = "priority") val priority: Int,
    @ColumnInfo(name = "deadline") val date: String?
) {
}