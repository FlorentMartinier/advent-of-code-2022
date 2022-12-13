import kotlin.math.min

fun main() {

    val head = Knot(0, 0, mutableListOf())
    val tail = Knot(0, 0, mutableListOf())
    val knotList = mutableListOf<Knot>()

    fun calcScorePart1(inputs: List<String>): Int {
        tail.saveHistoric()
        inputs.asSequence().forEach { line ->
            val(direction, distance) = line.split(" ")
            for (i in 0 until Integer.valueOf(distance)) {
                when(direction) {
                    "R" -> head.moveRight()
                    "L" -> head.moveLeft()
                    "U" -> head.moveUp()
                    else -> head.moveDown()
                }

                // Si les deux nœuds ne sont pas adjacents, alors ajuster le tail à la dernière position connue du head
                if (!tail.isTouchinOther(head)) {
                    tail.adjustCoordinate(head.positionHistoric[head.positionHistoric.lastIndex - 1])
                }
            }
        }

        return tail.positionHistoric.toSet().toList().size
    }

    fun calcScorePart2(inputs: List<String>): Int {
        for (i in 0 .. 9) {
            knotList.add(Knot(0, 0, mutableListOf()))
            knotList[i].saveHistoric()
        }
        inputs.asSequence().forEach { line ->
            println("line : $line")
            val(direction, distance) = line.split(" ")
            for (i in 0 until Integer.valueOf(distance)) {
                println("head before : ${knotList[0]}")
                when(direction) {
                    "R" -> knotList[0].moveRight()
                    "L" -> knotList[0].moveLeft()
                    "U" -> knotList[0].moveUp()
                    else -> knotList[0].moveDown()
                }
                println("head after : ${knotList[0]}")

                // Si deux nœuds ne sont pas adjacents, alors ajuster le nœud à la dernière position connue du nœud précédent
                for (knotIndex in 1 .. knotList.lastIndex) {
                    println("current knot : ${knotList[knotIndex]}. Other Knot : ${knotList[knotIndex - 1]}")
                    if (!knotList[knotIndex].isTouchinOther(knotList[knotIndex - 1])) {
                        knotList[knotIndex].adjustCoordinate(knotList[knotIndex - 1].positionHistoric[knotList[knotIndex - 1].positionHistoric.lastIndex - 1])
                    }
                }
            }
        }

        println(knotList)
        return knotList[knotList.lastIndex].positionHistoric.toSet().toList().size
    }

    val input = readInput("Day09")
    println("Answer Part 1: ${calcScorePart1(input)}")

    println("Answer Part 2: ${calcScorePart2(input)}")
}

class Knot(
    var x: Int,
    var y: Int,
    val positionHistoric: MutableList<String>
) {

    fun adjustCoordinate(input: String) {
        val (newX, newY) = input.split(" ")
        x = Integer.valueOf(newX)
        y = Integer.valueOf(newY)
        saveHistoric()
    }

    fun adjustCoordinateFromOther(other: Knot) {
        // Chercher toutes les positions possibles autour du point, en commençant par celles qui demandent le moins de trajet
        if (!isTouchinOther(x - 1, y, other)) {
            x--
        } else if (!isTouchinOther(x, y -1, other)) {
            y--
        } else if (!isTouchinOther(x + 1, y, other)) {
            x++
        } else if (!isTouchinOther(x, y + 1, other)) {
            y++
        } else if (!isTouchinOther(x + 1, y -1, other)) {
            x++
            y--
        } else if (!isTouchinOther(x - 1, y -1, other)) {
            x--
            y--
        } else if (!isTouchinOther(x + 1, y + 1, other)) {
            x++
            y++
        } else if (!isTouchinOther(x - 1, y + 1, other)) {
            x--
            y++
        }
        saveHistoric()
    }

    fun isTouchinOther(x: Int, y: Int, other: Knot): Boolean {
        return Knot(x, y, mutableListOf()).isTouchinOther(other)
    }

    fun isTouchinOther(other: Knot): Boolean {
        return isOverlapping(other) || isAdjacent(other) || isTouchingDiagonally(other)
    }

    override fun toString(): String {
        return "$x $y"
    }

    private fun isOverlapping(other: Knot): Boolean {
        return x == other.x && y == other.y
    }

    private fun isAdjacent(other: Knot): Boolean {
        return (y == other.y && (x == other.x + 1 || x == other.x -1)) ||
                (x == other.x && (y == other.y + 1 || y == other.y -1))
    }

    private fun isTouchingDiagonally(other: Knot): Boolean {
        return (y == other.y + 1 && (x == other.x + 1 || x == other.x -1)) ||
                (y == other.y - 1 && (x == other.x + 1 || x == other.x -1)) ||
                (x == other.x + 1 && (y == other.y + 1 || y == other.y -1)) ||
                (x == other.x - 1 && (y == other.y + 1 || y == other.y -1))
    }

    fun moveRight() {
        x++
        saveHistoric()
    }

    fun moveLeft() {
        x--
        saveHistoric()
    }

    fun moveUp() {
        y--
        saveHistoric()
    }

    fun moveDown() {
        y++
        saveHistoric()
    }

    fun saveHistoric() {
        positionHistoric.add(this.toString())
    }
}