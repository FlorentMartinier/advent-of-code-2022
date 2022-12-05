fun main() {

    fun giveScoreToCharacter(char: Char): Int {
        return if(char.isUpperCase()) {
            char.lowercaseChar().code - 96 + 26
        } else {
            char.code - 96
        }
    }

    /**
     * Return true if character in parameter is present in all list of strings in parameter
     */
    fun charPresentInAllCompartments(char: Char, compartments: List<String>): Boolean {
        return compartments
            .asSequence()
            .map { compartment -> compartment.contains(char) }
            .all { value -> value }
    }

    fun giveScoreToXCompartment(compartments: List<String>): Int {
        var score = 0
        val charactersList = mutableListOf<Char>()
        val firstCompartment = compartments[0]
        firstCompartment.forEach { character ->
            if (charPresentInAllCompartments(character, compartments.drop(1)) && !charactersList.contains(character)) {
                charactersList.add(character)
                score += giveScoreToCharacter(character)
            }
        }
        return score
    }

    fun calcScore(inputs: List<String>): Int {
        println(inputs)
        return inputs
            .asSequence()
            .chunked(3)
            .map { input -> giveScoreToXCompartment(input)}
            .sum()
    }

    val input = readInput("Day03")
    println("Answer: ${calcScore(input)}")
}