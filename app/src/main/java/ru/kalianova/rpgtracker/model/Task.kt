package ru.kalianova.rpgtracker.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import ru.kalianova.rpgtracker.model.TaskType.TaskType
import java.util.*

@Entity
data class Task(
    @Id var id: Long = 0,
    val name: String = "",
    @Transient
    val type: TaskType? = null,
    val difficulty: Int = 0,
    val priority: Int = 0,
    val date: Date? = null,
    val done: Date? = null
) {
}