fun main() {

    var cycleNb = 0
    var signalStrenght = 0
    var x = 1
    var spriteString = ""
    var lineNumber = 0

    fun mustSaveStrenght(): Boolean {
        return cycleNb == 20 || (cycleNb - 20) % 40 == 0
    }

    fun mustBreakLine(): Boolean {
        return cycleNb % 40 == 0
    }

    fun spriteIsVisible(): Boolean {
        return (cycleNb - 40 * lineNumber) in x .. x + 2
    }

    fun manageCycleIncrementStrenght() {
        cycleNb ++
        if (mustSaveStrenght()) {
            signalStrenght += x * cycleNb
        }

        spriteString += if (spriteIsVisible()) {
            "#"
        } else {
            "."
        }

        if (mustBreakLine()) {
            spriteString += "\n"
            lineNumber ++
        }
    }

    fun manageInstruction(line: String) {
        if (line == "noop") {
            manageCycleIncrementStrenght()
        } else {
            val(_, inscreaseXNb) = line.split(" ")
            for (i in 0 .. 1) {
                manageCycleIncrementStrenght()
            }
            x += Integer.valueOf(inscreaseXNb)
        }
    }

    fun calcScorePart1(inputs: List<String>): Int {
        for (i in inputs.indices) {
            val line = inputs[i]
            manageInstruction(line)
        }
        return signalStrenght
    }

    val input = readInput("Day10")
    println("Answer Part 1: ${calcScorePart1(input)}")

    println("Answer Part 2:")
    println(spriteString)
}
