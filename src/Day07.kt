fun main() {

    val historic = mutableListOf<String>()

    // Clé : un répertoire, valeur : La taille de tous les fichiers dans ce répertoire (sans conter la taille des sous répertoires)
    val mapDirSize = mutableMapOf<String, Int>()

    // Clé : un répertoire, valeur : List de tous les sous répertoire qu'il contient
    val mapDirSubDir = mutableMapOf<String, MutableList<String>>()

    // Clé : un répertoire, valeur : La taille de tous les fichiers dans ce répertoire (en comptant tous les sous repertoires)
    val mapDirSizeTotal = mutableMapOf<String, Int>()

    fun isCommandLine(line: String): Boolean {
        return line.toCharArray()[0] == '$'
    }

    fun getCurrentDir() : String {
        return historic[historic.lastIndex]
    }

    fun executeCommandLine(line: String) {
        val splittedCommand = line.split(" ")
        if (splittedCommand.contains("cd")) {
            if (splittedCommand[2] == "..") {
                historic.add(historic[historic.size - 2])
            } else {
                historic.add(splittedCommand[2])
            }
        } else if (splittedCommand.contains("ls")) {
            // Si le dossier a été visité, on remet à 0 et on recalcule la taille du dossier
            mapDirSize[getCurrentDir()] = 0
        }
    }

    fun addSizeToMap(sizeToAdd: Int) {
        mapDirSize[getCurrentDir()] = (mapDirSize[getCurrentDir()] ?: 0) + sizeToAdd
    }

    fun calcLineSize(line: String) {
        val (_, fileName) = line.split(" ")
        if(mapDirSubDir[getCurrentDir()] == null) {
            mapDirSubDir[getCurrentDir()] = mutableListOf()
        }
        if (!line.contains("dir")) {
            val (size, _) = line.split(" ")
            addSizeToMap(Integer.valueOf(size))
        } else {
            val newSubDirList = (mapDirSubDir[getCurrentDir()] ?: mutableListOf())
            newSubDirList.add(fileName)
            mapDirSubDir[getCurrentDir()] = newSubDirList
        }
    }

    fun calcTotalSizeOfDir(dirList: List<String>): Int {
        //println("dirList : $dirList")
        return dirList.sumOf { dirName ->
            //println("mapDirSubDir : $mapDirSubDir")
            val subDir = mapDirSubDir[dirName] ?: listOf()
            //println("subDir : $subDir")
            if (subDir.isNotEmpty()) {
                calcTotalSizeOfDir(subDir)
            } else {
                mapDirSize[dirName] ?: 0
            }
        }
    }

    fun fillMapTotalSize() {
        mapDirSubDir.forEach { (key, value) ->
            mapDirSizeTotal[key] = calcTotalSizeOfDir(value) + (mapDirSize[key] ?: 0)
        }
    }

    fun isSubDir(dirName: String, mapToVerification: Map<String, Int>): Boolean {
        mapDirSubDir.forEach { (key, value) ->
            if(value.contains(dirName) && mapToVerification.contains(key)) {
                return true
            }
        }
        return false
    }

    fun calcScore(inputs: List<String>): Int {
        inputs.asSequence().forEach { line ->
            if(isCommandLine(line)) {
                executeCommandLine(line)
            } else {
                calcLineSize(line)
            }
        }
        fillMapTotalSize()

        val filteredTotalMap = mapDirSizeTotal
            // Garder que les répertoires de taille inférieure à 100 000
            .filter { (_, value) ->
                value <= 100000 && value != 0
            }

        println("mapDirSize : $mapDirSize")
        println("mapDirSubDir : $mapDirSubDir")
        println("filteredTotalMap : $filteredTotalMap")

        return filteredTotalMap
            /*
            .filter { (key, value) ->
                !isSubDir(key, filteredTotalMap)
            }
             */
            .map { (key, value) ->
                value
            }
            .sum()
    }

    val input = readInput("Day07")
    println("Answer: ${calcScore(input)}")
}