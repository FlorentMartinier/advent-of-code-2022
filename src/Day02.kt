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
        return when (oppenentChoice) {
            "A" -> {
                if (myChoice == "Y") {
                    8
                } else {
                    3 // lose and Z = 3
                }
            }
            "B" -> {
                if (myChoice == "Z") {
                    9
                } else {
                    1 // lose and X = 1
                }
            }
            else -> {
                if (myChoice == "X") {
                    7
                } else {
                    2 // lose and Y = 2
                }
            }
        }
    }

    /**
     * if strategy X => need to lose
     * if strategy Y => need to tie
     * if strategy Z => need to win
     */
    fun calcMyChoice(opponentChoice: String, strategy: String): String {
        return when (opponentChoice) {
            "A" -> {
                when(strategy) {
                    "X" -> "Z"
                    "Y" -> "X"
                    else -> "Y"
                }
            }
            "B" -> {
                when(strategy) {
                    "X" -> "X"
                    "Y" -> "Y"
                    else -> "Z"
                }
            }
            else -> {
                when(strategy) {
                    "X" -> "Y"
                    "Y" -> "Z"
                    else -> "X"
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