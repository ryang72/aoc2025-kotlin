import kotlin.sequences.map

fun main() {
    fun part1(input: List<String>): Long {
        val tokens: List<List<String>> = input.map { row -> row.split("\\s+".toRegex()).filter { it.isNotEmpty() } }
        var ans = 0L
        for (col in 0..<tokens[0].size)
            ans += (0..<tokens.size - 1).asSequence()
                .map { tokens[it][col].toLong() }
                .reduce(if (tokens[tokens.size - 1][col] == "+") Long::plus else Long::times)
        return ans
    }

    fun part2(input: List<String>): Long {
        val longestInput = input.maxOf { it.length }
        val paddedInput = input.map { it.padEnd(longestInput) }

        var operatorIdx = 0
        var ans = 0L
        while (operatorIdx < paddedInput[paddedInput.size - 1].length) {
            val curOperatorIdx = operatorIdx

            do { operatorIdx++ }
            while (operatorIdx < paddedInput[paddedInput.size - 1].length && paddedInput[paddedInput.size - 1][operatorIdx] == ' ')

            var problemWidth = operatorIdx - curOperatorIdx
            if (operatorIdx < paddedInput[paddedInput.size - 1].length) problemWidth--

            val verticalNumbers = Array<Long>(problemWidth) { 0 }
            for (col in curOperatorIdx..<operatorIdx)
                for (row in 0..<paddedInput.size - 1)
                    if (paddedInput[row][col] != ' ')
                        verticalNumbers[col - curOperatorIdx] = verticalNumbers[col - curOperatorIdx] * 10 + paddedInput[row][col].digitToInt()
            ans += verticalNumbers.reduce(if (paddedInput[paddedInput.size - 1][curOperatorIdx] == '+') Long::plus else Long::times)
        }

        return ans
    }

    /**
     * 1: test -> 4277556 (5ms) | input -> 3785892992137 (3ms)
     * 2: test -> 3263827 (6ms) | input -> 7669802156452 (2ms)
     */
    runAndTime(6, ::part1, ::part2)
}