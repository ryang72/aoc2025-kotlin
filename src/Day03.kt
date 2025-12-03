import kotlin.math.max

fun main() {

    fun part1(input: List<String>): Int {
        var sum = 0
        for (bank in input) {
            var biggest = 0
            for (i in 0..<bank.length)
                for (j in i+1..<bank.length)
                    biggest = max(biggest, "${bank[i]}${bank[j]}".toInt())
            sum += biggest
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        var sum: Long = 0
        for (bank in input) {
            val memo: Array<Array<String>> = Array(13) { Array(bank.length + 1) { "0" } }
            for (batteries in 1..12)
                for (pos in batteries..bank.length)
                    memo[batteries][pos] = if (pos == 1) "${memo[0][pos]}${bank[0]}" else
                        max("${memo[batteries - 1][pos - 1]}${bank[pos - 1]}".toLong(), memo[batteries][pos - 1].toLong()).toString()
            sum += memo[12][bank.length].toLong()
        }
        return sum
    }

    val testInput = readInput("Day03_test")
    val input = readInput("Day03")

    println("Part 1 test input: ${part1(testInput)} input: ${part1(input)}")
    println("Part 2 test input: ${part2(testInput)} input: ${part2(input)}")
}