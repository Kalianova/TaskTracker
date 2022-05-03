package ru.kalianova.rpgtracker.model

import ru.kalianova.rpgtracker.model.TaskType.ColorList

data class Priority(
    val id: Int = 0,
    val name: String = ""
) {

    fun priorityList(): List<Priority> {
        return listOf(
            Priority(0, "1-й приоритет"),
            Priority(1, "2-й приоритет"),
            Priority(2, "3-й приоритет"),
            Priority(3, "4-й приоритет"),
            Priority(4, "5-й приоритет")
        )
    }
}