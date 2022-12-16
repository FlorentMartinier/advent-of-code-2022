import kotlin.math.floor

fun main() {

    var currentMonkeyIndex = -1
    val monkeys = mutableMapOf<Int, Monkey>()

    fun fillMonkeys(inputs: List<String>) {
        for (i in inputs.indices) {
            val line = inputs[i]

            // Empty line, treat next monkey
            if (line == "" || i ==  0) {
                currentMonkeyIndex++
                monkeys[currentMonkeyIndex] = Monkey()
                continue
            }
            val(lineDescription, lineValue) = try {
                line.split(":")
            } catch (e: Exception) {
                continue
            }

            // Initialize starting items
            if (lineDescription.contains("Starting items")){
                lineValue.split(", ")
                    .forEach { item ->
                        monkeys[currentMonkeyIndex]?.items?.add(Integer.valueOf(item.trim()).toLong())
                    }
            }

            // Initialize operation
            if (lineDescription.contains("Operation") && lineValue.contains("+")) {
                val (_, number) = lineValue.split("+")
                monkeys[currentMonkeyIndex]?.operation = { item ->
                    item + Integer.valueOf(number.trim())
                }
            }
            if (lineDescription.contains("Operation") && lineValue.contains("*")) {
                val (_, numberString) = lineValue.split("*")
                monkeys[currentMonkeyIndex]?.operation = { item ->
                    val number: Long = try {
                        Integer.valueOf(numberString.trim()).toLong()
                    } catch (e: Exception) {
                        item
                    }
                    item * number
                }
            }

            // Initialize Test
            if (lineDescription.contains("Test")) {
                val (_, number) = lineValue.split("by ")
                monkeys[currentMonkeyIndex]?.dividedBy = Integer.valueOf(number).toLong()
                monkeys[currentMonkeyIndex]?.test = { score ->
                    score % Integer.valueOf(number).toLong() == 0L
                }
            }
            if (lineDescription.contains("If true")) {
                val (_, monkeyIndex) = lineValue.split("monkey ")
                monkeys[currentMonkeyIndex]?.monkeyIdWhenTestTrue = Integer.valueOf(monkeyIndex)
            }
            if (lineDescription.contains("If false")) {
                val (_, monkeyIndex) = lineValue.split("monkey ")
                monkeys[currentMonkeyIndex]?.monkeyIdWhenTestFalse = Integer.valueOf(monkeyIndex)
            }
        }
    }

    fun inspectMonkey(monkey: Monkey?, manageAngry: () -> Unit) {
        val iterator = monkey?.items?.iterator()
        while(iterator?.hasNext() == true){
            val item = iterator.next()
            monkey.inspectItemNb ++
            monkey.angryScore = monkey.operation?.invoke(item) ?: 0
            manageAngry.invoke()
            if (monkey.test?.invoke(monkey.angryScore) == true) {
                monkeys[monkey.monkeyIdWhenTestTrue]?.items?.add(monkey.angryScore)
            } else {
                monkeys[monkey.monkeyIdWhenTestFalse]?.items?.add(monkey.angryScore)
            }
            //monkey.items.removeAt(0)
            iterator.remove()
            monkey.angryScore = 0
        }
    }

    fun calcResult(): Long{
        return monkeys.values
            .asSequence()
            .map { monkey -> monkey.inspectItemNb.toLong()}
            .sortedDescending()
            .take(2)
            .reduce { a, b -> a * b }
    }

    fun calcScorePart1(inputs: List<String>): Long {
        fillMonkeys(inputs)
        for (i in 0 until 20) {
            for (j in 0 until monkeys.size) {
                val monkey: Monkey? = monkeys[j]
                inspectMonkey(monkey) { monkey?.divideBy3Angry() }
            }
        }

        println(monkeys)
        return calcResult()
    }

    fun calcScorePart2(inputs: List<String>): Long {
        currentMonkeyIndex = -1
        monkeys.clear()
        fillMonkeys(inputs)

        // This solution is not from me. Found at : https://github.com/tginsberg/advent-2022-kotlin/blob/main/src/main/kotlin/com/ginsberg/advent2022/Day11.kt
        val testProduct = monkeys
            .map { it.value.dividedBy.toLong() }
            .reduce(Long::times)
        for (i in 0 until 10000) {
            for (j in 0 until monkeys.size) {
                val monkey: Monkey? = monkeys[j]
                inspectMonkey(monkey) { monkey?.manageAngry(testProduct) }
            }
        }

        println(monkeys)
        return calcResult()

    }

    val input = readInput("Day11")
    println("Answer Part 1: ${calcScorePart1(input)}")

    println("Answer Part 2: ${calcScorePart2(input)}")
}

class Monkey(
    var angryScore: Long = 0,
    val items: MutableList<Long> = mutableListOf(),
    var operation: ((item: Long) -> Long)? = null,
    var test: ((score: Long) -> Boolean)? = null,
    var monkeyIdWhenTestTrue: Int = 0,
    var monkeyIdWhenTestFalse: Int = 0,
    var inspectItemNb: Long = 0,
    var dividedBy: Long = 0,
) {

    fun divideBy3Angry() {
        angryScore = floor((angryScore / 3).toDouble()).toLong()
    }

    fun manageAngry(dividedBy: Long) {
        angryScore = angryScore.mod(dividedBy)
    }

    override fun toString(): String {
        return "inspectItemNb : $inspectItemNb"
    }
}
