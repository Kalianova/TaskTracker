package ru.kalianova.rpgtracker.model.TaskType

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import java.util.*

@Entity
data class TaskType(
    @Id var id: Long = 0,
    val name: String = "",
    val color: String = ""
) {
}