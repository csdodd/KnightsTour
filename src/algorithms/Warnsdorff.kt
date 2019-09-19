package algorithms

import grid.Grid

class Warnsdorff(private val grid: Grid) {

    fun run() {
        var currentCell = grid.randomCell()
        (1..grid.size()).forEach { _ ->
            val nextCell = currentCell?.neighbours()?.minBy { it.neighbours().size }
            currentCell?.link(nextCell)
            currentCell = nextCell
        }
    }
}