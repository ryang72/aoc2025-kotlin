import java.util.PriorityQueue
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt

data class Point(val id: Int, val x: Double, val y: Double, val z: Double)

fun dist(a: Point, b: Point): Double {
    return sqrt( abs(a.x - b.x).pow(2) + abs(a.y - b.y).pow(2) + abs(a.z - b.z).pow(2))
}

class UnionFind(size: Int) {

    val parent = Array(size) { it }
    var numComponents = size

    fun union(id1: Int, id2: Int) {
        val parent1 = find(id1)
        val parent2 = find(id2)

        if (parent1 == parent2) return

        numComponents -= 1
        parent[parent2] = parent1
    }

    fun find(id: Int): Int {
        if (parent[id] != id) parent[id] = find(parent[id])
        return parent[id]
    }

    fun getSortedConnectedComponentSizes(): List<Pair<Long, Int>> {
        return parent.groupBy { it }
            .map { (k, v) -> Pair(v.size.toLong(), k) }
            .sortedByDescending { it.first }
            .toList()
    }

}

fun main() {

    fun part1(input: List<String>): Long {
        val points = mutableListOf<Point>()
        for (id in 0..<input.size) {
            val nums = input[id].split(",").filter { it.isNotEmpty() }
            points.add(Point(id, nums[0].toDouble(), nums[1].toDouble(), nums[2].toDouble()))
        }

        val connections = PriorityQueue<Triple<Double, Int, Int>> { it1, it2 -> it1.first.compareTo(it2.first) }
        for (i in 0..<points.size)
            for (j in i+1..<points.size)
                connections.add(Triple(dist(points[i], points[j]),points[i].id, points[j].id))

        val unionFind = UnionFind(input.size)
        repeat(if (input.size > 20) 1000 else 10) {
            if (connections.isNotEmpty()) {
                val (_, id1, id2) = connections.remove()
                unionFind.union(id1, id2)
            }
        }

        (0..<input.size).forEach { i -> unionFind.find(i) }

        val sortedConnectedComponentSizes = unionFind.getSortedConnectedComponentSizes()
        return sortedConnectedComponentSizes[0].first * sortedConnectedComponentSizes[1].first * sortedConnectedComponentSizes[2].first
    }

    fun part2(input: List<String>): Long {
        val points = mutableListOf<Point>()
        for (id in 0..<input.size) {
            val nums = input[id].split(",").filter { it.isNotEmpty() }
            points.add(Point(id, nums[0].toDouble(), nums[1].toDouble(), nums[2].toDouble()))
        }

        val connections = PriorityQueue<Triple<Double, Point, Point>> { it1, it2 -> it1.first.compareTo(it2.first) }
        for (i in 0..<points.size)
            for (j in i+1..<points.size)
                connections.add(Triple(dist(points[i], points[j]),points[i], points[j]))

        val unionFind = UnionFind(input.size)
        var ans = 0L
        while (unionFind.numComponents > 1 && connections.isNotEmpty()) {
            val (_, pt1, pt2) = connections.remove()
            unionFind.union(pt1.id, pt2.id)
            ans = (pt1.x * pt2.x).toLong()
        }
        return ans
    }

    /**
     * 1: test -> 40 (9ms) | input -> 26400 (39ms)
     * 2: test -> 25272 (0ms) | input -> 8199963486 (37ms)
     */
    runAndTime(8, ::part1, ::part2)
}