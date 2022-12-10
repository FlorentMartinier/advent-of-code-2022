fun main() {

    val NB_CARACTERS_TO_DETECT_PACKET = 14

    fun isCharRepeated(string: String): Boolean {
        return string.toCharArray().distinct().size < NB_CARACTERS_TO_DETECT_PACKET
    }

    fun findMinScore(string: String): Int {
        for(i in string.indices) {
            if (!isCharRepeated(string.substring(i, i + NB_CARACTERS_TO_DETECT_PACKET))) {
                return i + NB_CARACTERS_TO_DETECT_PACKET
            }
        }
        return 0
    }

    fun calcScore(inputs: List<String>): Int {
        val input = inputs[0]
        return findMinScore(input)
    }

    val input = readInput("Day06")
    println("Answer: ${calcScore(input)}")
}