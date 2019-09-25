package output

import algorithms.Warnsdorff
import grid.Cell
import grid.Grid
import processing.core.PApplet


class KnightsTour : PApplet() {

    private val cellSize: Float = 40.0f
    private val numRows = 16
    private var grid: Grid? = null
    private var startCell: Cell? = null
    private var movesToRender = 1

    init {
        reset()
    }

    private fun reset() {
        grid = Grid(numRows, numRows)
        grid?.let { startCell = Warnsdorff(it).run() }
        movesToRender = 1
        frameRate = 30.0f
    }

    override fun settings() {
        size((cellSize * numRows).toInt() , (cellSize * numRows).toInt())
    }

    override fun setup() {
        frameRate(frameRate)
        background(255.0f, 255.0f, 255.0f, 255.0f)
    }

    override fun draw() {
        background(255)
        drawGrid()
        renderKnightMoves()
    }

    private fun drawGrid() {
        initGridStroke()
        grid?.forEachCell { cell ->
            fill(110.0f, 145.0f, 161.0f, 255.0f)
            if (cell.moveCount > movesToRender) {
                fill(110.0f, 145.0f, 161.0f, 128.0f)
            } else if(cell.moveCount == -1) {
                fill(210.0f, 145.0f, 161.0f, 128.0f)
            }
            val coords = cell.getCoords(cellSize)
            rect(coords.x1, coords.y1, cellSize, cellSize)
        }
    }

    private fun initGridStroke() {
        stroke(71.0f, 114.0f, 134.0f, 255.0f)
        strokeWeight(3.0f)
    }

    private fun renderKnightMoves() {
        stroke(17.0f,  60.0f, 81.0f, 255.0f)
        var currentCell = startCell
        (1..movesToRender).forEach { _ ->
            val coords = currentCell?.getCoords(cellSize)
            val nextCoords = currentCell?.linkedCell?.getCoords(cellSize)
            if (coords != null && nextCoords != null) {
                ellipse(coords.xC, coords.yC, cellSize / 8.0f, cellSize / 8.0f)
                ellipse(nextCoords.xC, nextCoords.yC, cellSize / 8.0f, cellSize / 8.0f)
                line(coords.xC, coords.yC, nextCoords.xC, nextCoords.yC)
            }
            currentCell = currentCell?.linkedCell
        }

        if (movesToRender < grid?.size() ?: 0) movesToRender += 1
        else reset()
    }
}