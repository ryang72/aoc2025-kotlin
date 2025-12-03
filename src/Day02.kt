import java.math.BigDecimal
import kotlin.math.pow

fun main() {

    fun repeatedNumberSequence(subDigits: Int, repeat: Int, lhs: BigDecimal, rhs: BigDecimal): Sequence<BigDecimal?> = sequence {
        var value = 10.0.pow(subDigits - 1).toLong()
        var possibleId = value.toString().repeat(repeat).toBigDecimal()
        while (possibleId.compareTo(rhs) <= 0) {
            if (possibleId.compareTo(lhs) >= 0) yield(possibleId)
            value++
            possibleId = value.toString().repeat(repeat).toBigDecimal()
        }
        yield(null)
    }

    fun part1(input: String): BigDecimal {
        val invalidIds = HashSet<BigDecimal>()
        val ranges = input.split(',')
        for (range in ranges) {
            val (lhs, rhs) = range.split('-')
            for (digits in lhs.length..rhs.length)
                if (digits % 2 == 0)
                    invalidIds.addAll(repeatedNumberSequence(digits / 2, 2, lhs.toBigDecimal(), rhs.toBigDecimal()).filterNotNull().toList())
        }
        return invalidIds.sumOf { it }
    }

    fun part2(input: String): BigDecimal {
        val invalidIds = HashSet<BigDecimal>()
        val ranges = input.split(',')
        for (range in ranges) {
            val (lhs, rhs) = range.split('-')
            for (digits in lhs.length..rhs.length)
                for (repeat in 2..digits)
                    if (digits % repeat == 0)
                        invalidIds.addAll(repeatedNumberSequence(digits / repeat, repeat, lhs.toBigDecimal(), rhs.toBigDecimal()).filterNotNull().toList())
        }
        return invalidIds.sumOf { it }
    }

    val testInput = readInput("Day02_test")
    val input = readInput("Day02")

    println("Part 1 test input: ${part1(testInput[0])} input: ${part1(input[0])}")
    println("Part 2 test input: ${part2(testInput[0])} input: ${part2(input[0])}")
}