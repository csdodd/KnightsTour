package grid

import java.awt.BasicStroke
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


open class Grid(private val numRows: Int, private val numColumns: Int) {

    private val grid by lazy { generateGrid() }

    private fun generateGrid(): Array<Array<Cell?>> {
        val emptyGrid = prepareGrid()
        return configureCells(emptyGrid)
    }

    open fun prepareGrid(): Array<Array<Cell?>> {
        return Array(numRows) {
                row -> Array<Cell?>(numColumns) {
                column ->
            Cell(row, column)
        }}
    }

    open fun configureCells(emptyGrid: Array<Array<Cell?>>): Array<Array<Cell?>> {
        emptyGrid.forEachIndexed { row, columns ->
            columns.forEachIndexed { column, cell ->
                cell?.northWest = emptyGrid[row - 2, column - 1]
                cell?.northEast = emptyGrid[row - 2, column + 1]
                cell?.southWest = emptyGrid[row + 2, column - 1]
                cell?.southEast = emptyGrid[row + 2, column + 1]
                cell?.westNorth = emptyGrid[row - 1, column - 2]
                cell?.westSouth = emptyGrid[row + 1, column - 2]
                cell?.eastNorth = emptyGrid[row - 1, column + 2]
                cell?.eastSouth = emptyGrid[row + 1, column + 2]
            }
        }

        return emptyGrid
    }

    fun randomCell(): Cell? {
        val r = (0 until numRows).random()
        val c = (0 until numColumns).random()
        return this[r, c]
    }

    fun size() = numRows * numColumns

    fun toPng(
        cellSize: Int = 10,
        fileName: String = "maze") {
        val img = BufferedImage(
            (cellSize * numColumns) + 1,
            (cellSize * numRows) + 1,
            BufferedImage.TYPE_INT_ARGB
        )

        val g2d = img.createGraphics()
        g2d.background = Color(0, 0, 0, 0)

        listOf("background", "walls", "knight").forEach { mode ->
            forEachCell { cell ->
                val coords = cell.getPngCoords(cellSize)

                when (mode) {
                    "background" -> {
                        g2d.color = colorForBackground()
                        g2d.fillRect(coords.x1, coords.y1, cellSize, cellSize)
                    }
                    "walls" -> {
                        g2d.color = colorForWalls()
                        g2d.drawLine(coords.x1, coords.y1, coords.x2, coords.y1)
                        g2d.drawLine(coords.x1, coords.y1, coords.x1, coords.y2)
                        g2d.drawLine(coords.x2, coords.y1, coords.x2, coords.y2)
                        g2d.drawLine(coords.x1, coords.y2, coords.x2, coords.y2)
                    }
                    "knight" -> {
                        g2d.color = colorForKnight()
                        g2d.stroke = BasicStroke(5.0f)
                        cell.linkedCell?.getPngCoords(cellSize)?.let { linkedCoords ->
                            g2d.drawLine(coords.xC, coords.yC, linkedCoords.xC, linkedCoords.yC)
                        }
                    }
                }
            }
        }

        ImageIO.write(img, "PNG", File("./", "$fileName.png"))
    }

    private fun colorForBackground(): Color = Color(110, 145, 161)
    private fun colorForWalls(): Color = Color(71, 114, 134)
    private fun colorForKnight(): Color = Color(17, 60, 81)

    private fun forEachCell(action: (Cell) -> Unit) {
        grid.forEach { row ->
            row.forEach { cell ->
                if (cell != null) {
                    action(cell)
                }
            }
        }
    }

    private operator fun get(row: Int, column: Int) = grid[row, column]

    private operator fun Array<Array<Cell?>>.get(row: Int, column: Int): Cell? {
        if (row !in 0 until numRows) return null
        if (column !in 0 until numColumns) return null
        return this[row][column]
    }
}