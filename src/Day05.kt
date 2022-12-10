fun main() {

    val mapCharacters = mutableMapOf<Int, MutableList<Char>>()
    var result = ""

    fun insertCratesInList(line: String) {
        val characters = line.toCharArray()
        for (i in 0 .. 8) {
            val letter = characters[i * 4 + 1]
            if (letter != ' ') {
                val newList = mapCharacters[i] ?: mutableListOf()
                newList.add(0, letter)
                mapCharacters[i] = newList
            }
        }
    }

    fun allCharsAreEmptyOrNumber(line: String): Boolean {
        return line.toCharArray().all { it == ' ' || it.isDigit() }
    }

    fun moveOneToOtherColumn(columnFrom: Int, columnTo: Int) {

        // Récupérer le dernier élément
        val listFrom = mapCharacters[columnFrom - 1] ?: mutableListOf()
        val lastElement = listFrom[listFrom.size - 1]

        // Ajouter cet élément dans la colonne target
        (mapCharacters[columnTo - 1] ?: mutableListOf()).add(lastElement)

        // Supprimer l'élément dans la colonne intiale
        listFrom.removeLast()
    }

    fun moveXToOtherColumnSinglely(x: Int, columnFrom: Int, columnTo: Int) {

        for (i in 1 .. x) {
            moveOneToOtherColumn(columnFrom, columnTo)
        }
    }

    fun moveXToOtherColumnGroupely(x: Int, columnFrom: Int, columnTo: Int) {

        // Récupérer les X derniers éléments
        val listFrom = mapCharacters[columnFrom - 1] ?: mutableListOf()
        for (i in listFrom.size - x until listFrom.size) {
            val element = listFrom[i]
            (mapCharacters[columnTo - 1] ?: mutableListOf()).add(element)
        }

        for (i in 0 until x) {
            // Supprimer les éléments dans la colonne initiale
            listFrom.removeLast()
        }
    }

    fun calcScore(inputs: List<String>): String {
        var initilizing = true;
        inputs.asSequence()
            .forEachIndexed { index, line ->
                if (initilizing && allCharsAreEmptyOrNumber(line)) {
                    initilizing = false
                }
                if (initilizing) {
                    insertCratesInList(line)
                } else if (!allCharsAreEmptyOrNumber(line)) {
                    val infos = line.split(' ')
                    val nbToMove = Integer.valueOf(infos[1])
                    val columnFrom = Integer.valueOf(infos[3])
                    val columnTo = Integer.valueOf(infos[5])

                    // moveXToOtherColumnSinglely(nbToMove, columnFrom, columnTo)
                    moveXToOtherColumnGroupely(nbToMove, columnFrom, columnTo)
                }
            }
        mapCharacters
            .toSortedMap()
            .forEach { (key, value) ->
                result += value[value.lastIndex]
            }
        return result
    }

    val input = readInput("Day05")
    println("Answer: ${calcScore(input)}")
}