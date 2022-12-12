fun main() {

    val listVisibleTrees = mutableListOf<String>()
    val maxNumberFromTop = mutableMapOf<Int, Int>()
    val maxNumberFromBottom = mutableMapOf<Int, Int>()

    var maxScoreFromPosition = 0

    fun getNumberFromLineIndice(line: String, indice: Int): Int {
        return Integer.valueOf(line.toCharArray()[indice].toString())
    }

    fun buildCoordinate(lineIndice: Int, columnIndice: Int): String {
        return "$lineIndice $columnIndice"
    }

    fun coordinateIsAlreadyFound(lineIndice: Int, columnIndice: Int): Boolean {
        val coordinates = buildCoordinate(lineIndice, columnIndice)
        return listVisibleTrees.contains(coordinates)
    }

    fun calcRightFromPosition(lineIndice: Int, columnIndice: Int, inputs: List<String>): Int {
        val line = inputs[columnIndice]
        var nbViewTrees = 0
        val maxHeightTree = getNumberFromLineIndice(line, lineIndice)
        var maxHeightOverriden = false
        for (i in lineIndice + 1 .. line.lastIndex) {
            val nb = getNumberFromLineIndice(line, i)
            if (nb < maxHeightTree && !maxHeightOverriden) {
                nbViewTrees++
            } else if (nb >= maxHeightTree && !maxHeightOverriden) {
                nbViewTrees++
                maxHeightOverriden = true
            }
        }
        return nbViewTrees
    }

    fun calcLeftFromPosition(lineIndice: Int, columnIndice: Int, inputs: List<String>): Int {
        val line = inputs[columnIndice]
        var nbViewTrees = 0
        val maxHeightTree = getNumberFromLineIndice(line, lineIndice)
        var maxHeightOverriden = false
        for (i in lineIndice - 1 downTo 0) {
            val nb = getNumberFromLineIndice(line, i)
            if (nb < maxHeightTree && !maxHeightOverriden) {
                nbViewTrees++
            } else if (nb >= maxHeightTree && !maxHeightOverriden) {
                nbViewTrees++
                maxHeightOverriden = true
            }
        }
        return nbViewTrees
    }

    fun calcTopFromPosition(lineIndice: Int, columnIndice: Int, inputs: List<String>): Int {
        var nbViewTrees = 0
        val maxHeightTree = getNumberFromLineIndice(inputs[columnIndice], lineIndice)
        var maxHeightOverriden = false
        for (i in columnIndice - 1 downTo 0) {
            val line = inputs[i]
            val nb = getNumberFromLineIndice(line, lineIndice)
            if (nb < maxHeightTree && !maxHeightOverriden) {
                nbViewTrees++
            } else if (nb >= maxHeightTree && !maxHeightOverriden) {
                nbViewTrees++
                maxHeightOverriden = true
            }
        }
        return nbViewTrees
    }

    fun calcBottomFromPosition(lineIndice: Int, columnIndice: Int, inputs: List<String>): Int {
        var nbViewTrees = 0
        val maxHeightTree = getNumberFromLineIndice(inputs[columnIndice], lineIndice)
        var maxHeightOverriden = false
        for (i in columnIndice + 1 .. inputs.lastIndex) {
            val line = inputs[i]
            val nb = getNumberFromLineIndice(line, lineIndice)
            if (nb < maxHeightTree && !maxHeightOverriden) {
                nbViewTrees++
            } else if (nb >= maxHeightTree && !maxHeightOverriden) {
                nbViewTrees++
                maxHeightOverriden = true
            }
        }
        return nbViewTrees
    }

    fun calcScoreFromPosition(lineIndice: Int, columnIndice: Int, inputs: List<String>): Int {
        return calcLeftFromPosition(lineIndice, columnIndice, inputs) *
                calcRightFromPosition(lineIndice, columnIndice, inputs) *
                calcTopFromPosition(lineIndice, columnIndice, inputs) *
                calcBottomFromPosition(lineIndice, columnIndice, inputs)
    }

    fun calcVisibleTreesLine (line: String, lineIndice: Int) {
        var maxNumberFromLeft = -1
        var maxNumberFromRight = -1
        for(i in line.indices) {

            // Calc from left to right
            val nb = getNumberFromLineIndice(line, i)
            if (nb > maxNumberFromLeft && !coordinateIsAlreadyFound(lineIndice, i)) {
                listVisibleTrees.add(buildCoordinate(lineIndice, i))
            }
            if (nb > maxNumberFromLeft) {
                maxNumberFromLeft = nb
            }

            // Calc from top to bottom
            if (maxNumberFromTop[i] == null) {
                maxNumberFromTop[i] = -1
            }
            if (nb > (maxNumberFromTop[i] ?: nb) && !coordinateIsAlreadyFound(lineIndice, i)) {
                listVisibleTrees.add(buildCoordinate(lineIndice, i))
            }
            if (nb > (maxNumberFromTop[i] ?: nb)) {
                maxNumberFromTop[i] = nb
            }
        }

        // Calc from right to left
        for(i in line.lastIndex downTo 0) {
            val nb = getNumberFromLineIndice(line, i)
            if (nb > maxNumberFromRight && !coordinateIsAlreadyFound(lineIndice, i)) {
                listVisibleTrees.add(buildCoordinate(lineIndice, i))
            }
            if (nb > maxNumberFromRight) {
                maxNumberFromRight = nb
            }
        }
    }

    fun calcVisibleTreesLineFromBottom (line: String, lineIndice: Int) {
        // Calc from bottom to top
        for(i in line.indices) {
            val nb = getNumberFromLineIndice(line, i)
            if (maxNumberFromBottom[i] == null) {
                maxNumberFromBottom[i] = -1
            }
            if (nb > (maxNumberFromBottom[i] ?: nb) && !coordinateIsAlreadyFound(lineIndice, i)) {
                listVisibleTrees.add(buildCoordinate(lineIndice, i))
            }
            if (nb > (maxNumberFromBottom[i] ?: -1)) {
                maxNumberFromBottom[i] = nb
            }
        }
    }

    fun calcScorePart1(inputs: List<String>): Int {
        for (inputNb in inputs.indices) {
            val line = inputs[inputNb]
            calcVisibleTreesLine(line, inputNb)
        }

        for (inputNb in inputs.lastIndex downTo 0) {
            val line = inputs[inputNb]
            calcVisibleTreesLineFromBottom(line, inputNb)
        }

        return listVisibleTrees.size
    }

    fun calcScorePart2(inputs: List<String>): Int {
        for (columnIndice in inputs.indices) {
            val line = inputs[columnIndice]
            for(lineIndice in line.indices) {
                val scoreFromPosition = calcScoreFromPosition(lineIndice, columnIndice, inputs)
                if (scoreFromPosition > maxScoreFromPosition) {
                    maxScoreFromPosition = scoreFromPosition
                }
            }
        }

        return maxScoreFromPosition
    }

    val input = readInput("Day08")
    println("Answer Part 1: ${calcScorePart1(input)}")

    println("Answer Part 2: ${calcScorePart2(input)}")
}