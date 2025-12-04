import java.util.LinkedList

fun main() {

    fun getValidAdj(x: Int, y: Int, xMax: Int, yMax: Int): List<Pair<Int, Int>> {
        return sequenceOf(Pair(-1, -1), Pair(-1, 0), Pair(-1, 1), Pair(0, -1), Pair(0, 1), Pair(1, -1), Pair(1, 0), Pair(1, 1))
            .map { Pair(it.first + x, it.second + y) }
            .filter { it.first in 0..<xMax && it.second in 0..<yMax }
            .toList()
    }

    fun findRolls(map: MutableList<MutableList<Char>>, shouldRemove: Boolean): Int {
        var ans = 0
        for (x in 0..<map.size)
            for (y in 0..<map[x].size)
                if (map[x][y] == '@') {
                    val adjRolls = getValidAdj(x, y, map.size, map[x].size)
                        .count { map[it.first][it.second] == '@' }
                    if (adjRolls < 4) {
                        ans += 1
                        if (shouldRemove) map[x][y] = 'x'
                    }
                }
        return ans
    }

    fun part1(input: List<String>): Int {
        val mutableMap = input.asSequence()
            .map { row -> row.toMutableList() }
            .toMutableList()
        return findRolls(mutableMap, false)
    }

    fun part2(input: List<String>): Int {
        val mutableMap = input.asSequence()
            .map { row -> row.toMutableList() }
            .toMutableList()
        var totalRolls = 0; var newRolls = 0
        do {
            newRolls = findRolls(mutableMap, true)
            totalRolls += newRolls
        } while (newRolls > 0)
        return totalRolls
    }

    fun part2Queue(input: List<String>): Int {
        val map: List<MutableList<String>> = input.asSequence()
            .map { row -> row.split("").filter { it.isNotEmpty() }.toMutableList() }
            .toList()

        var totalRolls = 0;
        val toBeCleaned = LinkedList<Pair<Int, Int>>()
        for (x in 0..<map.size)
            for (y in 0..<map[x].size)
                if (map[x][y] == "@") {
                    val adjRolls = getValidAdj(x, y, map.size, map[x].size)
                        .count { map[it.first][it.second] != "." }
                    map[x][y] = adjRolls.toString()
                    if (adjRolls < 4) toBeCleaned.add(Pair(x, y))
                }

        while (toBeCleaned.isNotEmpty()) {
            val (x, y) = toBeCleaned.pop()
            if (map[x][y] == ".") continue
            map[x][y] = "."; totalRolls++

            for ((adjX, adjY) in getValidAdj(x, y, map.size, map[x].size))
                if (map[adjX][adjY] != ".") {
                    val decremented = map[adjX][adjY].toInt() - 1
                    map[adjX][adjY] = decremented.toString()
                    if (decremented < 4) toBeCleaned.add(Pair(adjX, adjY))
                }
        }

        return totalRolls
    }

    /**
     * 1: test -> 13 (11ms) | input -> 1435 (15ms)
     * 2: test -> 43 (0ms) | input -> 8623 (62ms)
     * 3: test -> 43 (3ms) | input -> 8623 (22ms)
     */
    runAndTime(4, ::part1, ::part2, ::part2Queue)
}