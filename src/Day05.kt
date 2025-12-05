fun main() {

    fun part1(input: List<String>): Int {
        val ranges = mutableSetOf<Pair<Long,Long>>()
        var seenEmpty = false
        var ans = 0
        for (line in input) {
            if (line.isEmpty()) {
                seenEmpty = true
                continue
            }
            if (!seenEmpty) {
                val (lhs, rhs) = line.split('-').map { it.toLong() }
                ranges.add(Pair(lhs, rhs))
            } else {
                for (range in ranges) {
                    if (line.toLong() in range.first..range.second) {
                        ans++
                        break
                    }
                }
            }
        }
        return ans
    }

    fun part2(input: List<String>): Long {
        val sortedInput = mutableListOf<Pair<Long,Long>>()
        for (line in input) {
            if (line.isEmpty()) break
            val (lhs, rhs) = line.split('-').map { it.toLong() }
            sortedInput.add(Pair(lhs, rhs))
        }
        sortedInput.sortWith(compareBy<Pair<Long, Long>> { it.first }.thenBy { it.second })

        val ranges = mutableListOf<Pair<Long,Long>>()
        for (line in sortedInput) {
            var maybeRangeToAdd: Pair<Long, Long>? = Pair(line.first, line.second)
            for (range in ranges.toSet())
                when {
                    line.first < range.first && range.second < line.second -> {
                        ranges.remove(Pair(range.first, range.second))
                        break
                    }
                    line.first < range.first && line.second in range.first..range.second -> {
                        ranges.remove(Pair(range.first, range.second))
                        maybeRangeToAdd = Pair(line.first, range.second)
                        break
                    }
                    line.first in range.first..range.second && range.second < line.second -> {
                        ranges.remove(Pair(range.first, range.second))
                        maybeRangeToAdd = Pair(range.first, line.second)
                        break
                    }
                    line.first in range.first..range.second && line.second in range.first..range.second -> {
                        maybeRangeToAdd = null
                        break
                    }
                }
            maybeRangeToAdd?.let { ranges.add(Pair(it.first, it.second)) }
        }

        return ranges.asSequence()
            .map { it.second - it.first + 1 }
            .sum()
    }

    /**
     * 1: test -> 3 (2ms) | input -> 888 (9ms)
     * 2: test -> 14 (3ms) | input -> 344378119285354 (3ms)
     */
    runAndTime(5, ::part1, ::part2)
}