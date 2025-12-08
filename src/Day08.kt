import java.util.PriorityQueue
import kotlin.math.pow

data class Point(val id: Int, val x: Double, val y: Double, val z: Double)

fun distPow2(a: Point, b: Point): Double {
    return (a.x - b.x).pow(2) + (a.y - b.y).pow(2) + (a.z - b.z).pow(2)
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

}

fun main() {

    fun inputToSortedConnections(input: List<String>): PriorityQueue<Triple<Double, Point, Point>> {
        val points = mutableListOf<Point>()
        for (id in 0..<input.size) {
            val (x , y, z) = input[id].split(",").filter { it.isNotEmpty() }.map { it.toDouble() }
            points.add(Point(id, x, y, z))
        }
        val connections = PriorityQueue<Triple<Double, Point, Point>> { it1, it2 -> it1.first.compareTo(it2.first) }
        for (i in 0..<points.size)
            for (j in i+1..<points.size)
                connections.add(Triple(distPow2(points[i], points[j]),points[i], points[j]))
        return connections
    }

    fun part1(input: List<String>): Long {
        val connections = inputToSortedConnections(input)

        val unionFind = UnionFind(input.size)
        repeat(if (input.size <= 20) 10 else 1000) {
            connections.poll()?.let { unionFind.union(it.second.id, it.third.id) }
        }

        (0..<input.size).forEach(unionFind::find)
        return unionFind.parent.groupBy { it }
            .map { (_, v) -> v.size.toLong() }
            .sortedByDescending { it }
            .take(3)
            .reduce(Long::times)
    }

    fun part2(input: List<String>): Long {
        val connections = inputToSortedConnections(input)
        val unionFind = UnionFind(input.size)
        while (connections.isNotEmpty()) {
            val (_, pt1, pt2) = connections.remove()
            unionFind.union(pt1.id, pt2.id)
            if (unionFind.numComponents == 1) return (pt1.x * pt2.x).toLong()
        }
        return -1
    }

    /**
     * 1: test -> 40 (9ms) | input -> 26400 (35ms)
     * 2: test -> 25272 (0ms) | input -> 8199963486 (27ms)
     */
    runAndTime(8, ::part1, ::part2)
}