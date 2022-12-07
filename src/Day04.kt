import kotlin.math.max

fun main() {

    fun buildRange(rangeString: String): Range {
        val rangeString = rangeString.split("-")
        return Range(Integer.valueOf(rangeString[0]), Integer.valueOf(rangeString[1]))
    }

    fun calcScore(inputs: List<String>): Int {
        return inputs.asSequence()
            // Map to List of Ranges
            .map { input ->
                val rangeStringList = input.split(",")
                val range1 = buildRange(rangeStringList[0])
                val range2 = buildRange(rangeStringList[1])

                // if fully contain, return 1, else 0
                if(range1.isOverlaped(range2)) {
                    1
                } else {
                    0
                }
            }
            .sum()
    }

    val input = readInput("Day04")
    println("Answer: ${calcScore(input)}")
}

class Range constructor (
    private val minNumber: Int,
    private val maxNumber: Int
) {

    fun fullyContainByOther(other: Range): Boolean {
        return (minNumber >= other.minNumber && maxNumber <= other.maxNumber) ||
                (other.minNumber >= minNumber && other.maxNumber <= maxNumber)
    }

    fun isOverlaped(other: Range): Boolean {
        return (minNumber in other.minNumber .. other.maxNumber) ||
                (maxNumber in other.minNumber .. other.maxNumber) ||
                (other.minNumber in minNumber..maxNumber) ||
                (other.maxNumber in minNumber..maxNumber)
    }
}