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

    fun part1(input: List<String>): BigDecimal {
        val invalidIds = HashSet<BigDecimal>()
        val ranges = input[0].split(',')
        for (range in ranges) {
            val (lhs, rhs) = range.split('-')
            for (digits in lhs.length..rhs.length)
                if (digits % 2 == 0)
                    invalidIds.addAll(repeatedNumberSequence(digits / 2, 2, lhs.toBigDecimal(), rhs.toBigDecimal()).filterNotNull().toList())
        }
        return invalidIds.sumOf { it }
    }

    fun part2(input: List<String>): BigDecimal {
        val invalidIds = HashSet<BigDecimal>()
        val ranges = input[0].split(',')
        for (range in ranges) {
            val (lhs, rhs) = range.split('-')
            for (digits in lhs.length..rhs.length)
                for (repeat in 2..digits)
                    if (digits % repeat == 0)
                        invalidIds.addAll(repeatedNumberSequence(digits / repeat, repeat, lhs.toBigDecimal(), rhs.toBigDecimal()).filterNotNull().toList())
        }
        return invalidIds.sumOf { it }
    }

    /**
     * 1: test -> 1227775554 (13ms) | input -> 9188031749 (11ms)
     * 2: test -> 4174379265 (2ms) | input -> 11323661261 (3ms)
     */
    runAndTime(2, ::part1, ::part2)
}