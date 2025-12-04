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

    /**
     * 1: test -> 3 (1ms) | input -> 1040 (1ms)
     * 2: test -> 6 (0ms) | input -> 6027 (0ms)
     */
    runAndTime(1, ::part1, ::part2)
}
