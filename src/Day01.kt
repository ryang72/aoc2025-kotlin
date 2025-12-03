import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        var ans = 0
        var pos = 50

        for (line in input) {
            val dist = line.drop(1).toInt()
            when (line[0]) {
                'L' -> pos -= dist
                'R' -> pos += dist
            }
            pos %= 100
            if (pos == 0) ans++
        }

        return ans
    }

    fun part2(input: List<String>): Int {
        var ans = 0
        var pos = 50

        for (line in input) {
            val dist = line.drop(1).toInt()
            val notFromZero = pos != 0
            when (line[0]) {
                'L' -> pos -= dist
                'R' -> pos += dist
            }
            if (pos == 0) ans++
            if (pos < 0 && notFromZero) ans++
            ans += abs(pos) / 100
            pos = pos.mod(100)
        }

        return ans
    }

    val testInput = readInput("Day01_test")
    val input = readInput("Day01")

    println("Part 1 test input: ${part1(testInput)} input: ${part1(input)}")
    println("Part 2 test input: ${part2(testInput)} input: ${part2(input)}")
}
