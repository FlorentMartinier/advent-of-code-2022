fun main() {

    fun calcMaxCalories(input: List<String>): Int {
        return input
            .joinToString()
            .filter { !it.isWhitespace() }
            .split(",,")
            .maxOfOrNull { inputString ->
                inputString
                    .split(",")
                    .sumOf { Integer.valueOf(it) }
            } ?: 0
    }

    val input = readInput("Day01")
    println("Answer: ${calcMaxCalories(input)}")
}