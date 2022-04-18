package ru.kalianova.rpgtracker.model.TaskType

data class ColorList(
    val id: Int = 0,
    val name: String = "",
    val code: String = ""
) {

    fun basicColors(): List<ColorList> {
        return listOf(
            ColorList(0, "Black", "#000000"),
            ColorList(1, "Silver", "#C0C0C0"),
            ColorList(2, "Grey", "#808080"),
            ColorList(3, "Maroon", "#800000"),
            ColorList(4, "Red", "#FF0000"),
            ColorList(5, "Fucsia", "#FF00FF"),
            ColorList(6, "Green", "#008000"),
            ColorList(7, "Liam", "#00FF00"),
            ColorList(8, "Olive", "#808000"),
            ColorList(9, "Yellow", "#FFFF00"),
            ColorList(10, "Navy", "#000080"),
            ColorList(11, "Blue", "#0000FF"),
            ColorList(12, "Teal", "#008080"),
            ColorList(13, "Aqua", "#00FFFF")
        )
    }
}
