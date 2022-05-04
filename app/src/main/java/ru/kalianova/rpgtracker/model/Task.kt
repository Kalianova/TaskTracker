package ru.kalianova.rpgtracker.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import ru.kalianova.rpgtracker.model.TaskType.TaskType
import java.util.*

@Entity
data class Task(
    @Id var id: Long = 0,
    val name: String = "",
    val description: String ="",
    val type: Long, //TaskType
    val project: Long, //Project
    val priority: Int = 0,
    val deadline: Date? = null,
    var done: Date? = null,
    val global: Int? = null
) {
}