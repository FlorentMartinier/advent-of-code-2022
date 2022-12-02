fun main() {

    fun calcScoreChoice(myChoice: String): Int {
        return when(myChoice) {
            "X" -> 1
            "Y" -> 2
            else -> 3
        }
    }

    fun roundIsTied(oppenentChoice: String, myChoice: String): Boolean {
        return (oppenentChoice == "A" && myChoice == "X") ||
                (oppenentChoice == "B" && myChoice == "Y") ||
                (oppenentChoice == "C" && myChoice == "Z")
    }

    fun calcScoreForRound(oppenentChoice: String, myChoice: String): Int {
        if (roundIsTied(oppenentChoice, myChoice)) {
            return 3 + calcScoreChoice(myChoice)
        }
        when (oppenentChoice) {
            "A" -> {
                return if (myChoice == "Y") {
                    8
                } else {
                    3 // lose and Z = 3
                }
            }
            "B" -> {
                return if (myChoice == "Z") {
                    9
                } else {
                    1 // lose and X = 1
                }
            }
            else -> {
                return if (myChoice == "X") {
                    7
                } else {
                    2 // lose and Y = 2
                }
            }
        }
    }

    fun calcMyChoice(opponentChoice: String, strategy: String): String {
        when (opponentChoice) {
            "A" -> {
                return when(strategy) {
                    "X" -> "Z" // Need to lose
                    "Y" -> "X" // Need to tied
                    else -> "Y" // Need to win
                }
            }
            "B" -> {
                return when(strategy) {
                    "X" -> "X" // Need to lose
                    "Y" -> "Y" // Need to tied
                    else -> "Z" // Need to win
                }
            }
            else -> {
                return when(strategy) {
                    "X" -> "Y" // Need to lose
                    "Y" -> "Z" // Need to tied
                    else -> "X" // Need to win
                }
            }
        }
    }

    fun calcScore(input: List<String>): Int {
        return input.asSequence()
            .map { round ->
                val splittedRound = round.split(" ")
                val opponentChoice = splittedRound[0]
                val strategy = splittedRound[1]
                val myChoice = calcMyChoice(opponentChoice, strategy)
                calcScoreForRound(opponentChoice, myChoice)
            }
            .sum()
    }

    val input = readInput("Day02")
    println("Answer: ${calcScore(input)}")
}