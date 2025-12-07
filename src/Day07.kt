fun main() {

    fun part1(input: List<String>): Int {
        val grid: List<MutableList<String>> = input.map { line -> line.split("").filter { it.isNotEmpty() }.toMutableList() }
        var splitCount = 0
        for (i in 1..<grid.size)
            for (j in 0..<grid[i].size)
                if (grid[i - 1][j] in listOf("S", "|"))
                    when (grid[i][j]) {
                        "." -> grid[i][j] = "|"
                        "^" -> {
                            if (j - 1 > 0) grid[i][j - 1] = "|"
                            if (j + 1 < grid[i].size) grid[i][j + 1] = "|"
                            splitCount++
                        }
                    }
        return splitCount
    }

    fun countTimelines(grid: List<MutableList<String>>, x: Int, y: Int): Long {
        if (y < 0 || y >= grid[x].size) return 0
        if (x + 1 >= grid.size) return 1
        if (grid[x][y] !in listOf(".", "S")) return grid[x][y].toLong()
        val ans = if (grid[x + 1][y] != "^") countTimelines(grid, x + 1, y) else countTimelines(grid, x + 1, y - 1) + countTimelines(grid, x + 1, y + 1)
        grid[x][y] = ans.toString()
        return ans
    }

    fun part2(input: List<String>): Long {
        val grid: List<MutableList<String>> = input.map { line -> line.split("").filter { it.isNotEmpty() }.toMutableList() }
        var ans = -1L
        for (i in 0..<grid[0].size)
            if (grid[0][i] == "S")
                ans = countTimelines(grid, 0, i)
        return ans
    }

    /**
     * 1: test -> 21 (9ms) | input -> 1660 (11ms)
     * 2: test -> 40 (0ms) | input -> 305999729392659 (5ms)
     */
    runAndTime(7, ::part1, ::part2)
}