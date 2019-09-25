package grid

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

    fun forEachCell(action: (Cell) -> Unit) {
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