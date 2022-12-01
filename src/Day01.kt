fun main() {

    fun calcMaxCalories(input: List<String>): Int {
        val listOfCaloriesSorted = input
            .joinToString()
            .filter { !it.isWhitespace() }
            .split(",,")
            .map { inputString ->
                inputString
                    .split(",")
                    .sumOf { Integer.valueOf(it) }
            }
            .sortedDescending()
            .toList()
        return listOfCaloriesSorted[0] + listOfCaloriesSorted[1] + listOfCaloriesSorted[2]
    }

    val input = readInput("Day01")
    println("Answer: ${calcMaxCalories(input)}")
}