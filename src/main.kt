import algorithms.Warnsdorff
import grid.Grid

fun main(args: Array<String>) {
    val grid = Grid(150, 150)
    Warnsdorff(grid).run()
    grid.toPng(cellSize = 20)
}