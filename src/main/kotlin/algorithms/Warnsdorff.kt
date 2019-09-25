package algorithms

import grid.Cell
import grid.Grid

class Warnsdorff(private val grid: Grid) {

    fun run(): Cell? {
        val startCell = grid.randomCell()
        var currentCell = startCell
        (0 until grid.size()).forEach { moveCount ->
            val groupedNeighbours = currentCell
                                    ?.neighbours()
                                    ?.groupBy { it.neighbours().size }

            val lowestAmountOfNeighbours = groupedNeighbours?.keys?.min()

            val nextCell = groupedNeighbours
                            ?.get(lowestAmountOfNeighbours)
                            ?.random()

            currentCell?.link(nextCell)
            currentCell?.moveCount = moveCount
            currentCell = nextCell
        }

        return startCell
    }
}