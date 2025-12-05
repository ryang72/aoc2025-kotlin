import java.math.BigInteger
import java.security.MessageDigest
import java.text.DecimalFormat
import kotlin.io.path.Path
import kotlin.io.path.readText
import kotlin.time.measureTime

val DECIMAL_FORMAT = DecimalFormat("00")

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = Path("src/$name.txt").readText().trim().lines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

fun runAndTime(day: Int, vararg solutions: (List<String>) -> Any) {
    val testInput = readInput("Day${DECIMAL_FORMAT.format(day)}_test")
    val input = readInput("Day${DECIMAL_FORMAT.format(day)}")

    for ((idx, solution) in solutions.withIndex()) {
        var testAns: Any
        val testTimeTaken = measureTime { testAns = solution(testInput) }

        var realAns: Any
        val realTimeTaken = measureTime { realAns = solution(input) }

        println("${idx + 1}: test -> $testAns (${testTimeTaken.inWholeMilliseconds}ms) | input -> $realAns (${realTimeTaken.inWholeMilliseconds}ms)")
    }
}

fun runAndTimeTestOnly(day: Int, vararg solutions: (List<String>) -> Any) {
    val testInput = readInput("Day${DECIMAL_FORMAT.format(day)}_test")

    for ((idx, solution) in solutions.withIndex()) {
        var testAns: Any
        val testTimeTaken = measureTime { testAns = solution(testInput) }

        println("${idx + 1}: test -> $testAns (${testTimeTaken.inWholeMilliseconds}ms)")
    }
}