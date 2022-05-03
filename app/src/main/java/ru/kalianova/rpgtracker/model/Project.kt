package ru.kalianova.rpgtracker.model

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Project(
    @Id var id: Long = 0,
    val name: String = ""
)
