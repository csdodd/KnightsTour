package grid

open class Cell(
    private val row: Int,
    private val column: Int
) {
    /*

    Labels of the possible knight moves from the Knights original position (K)

          NW  _   _ NE
               | |
       WN |    | |    | EN
           ==== K ====
       WS |    | |    | ES
               | |
          SW  _   _ SE

     */

    var northWest: Cell? = null
    var northEast: Cell? = null
    var southWest: Cell? = null
    var southEast: Cell? = null
    var westNorth: Cell? = null
    var westSouth: Cell? = null
    var eastNorth: Cell? = null
    var eastSouth: Cell? = null
    var linkedCell: Cell? = null
    var moveCount: Int = -1

    private var visited: Boolean = false

    fun neighbours(): List<Cell> {
        val neighbours = mutableListOf<Cell>()
        northWest?.let { if(!it.visited) neighbours.add(it) }
        northEast?.let { if(!it.visited) neighbours.add(it) }
        southWest?.let { if(!it.visited) neighbours.add(it) }
        southEast?.let { if(!it.visited) neighbours.add(it) }
        westNorth?.let { if(!it.visited) neighbours.add(it) }
        westSouth?.let { if(!it.visited) neighbours.add(it) }
        eastNorth?.let { if(!it.visited) neighbours.add(it) }
        eastSouth?.let { if(!it.visited) neighbours.add(it) }
        return neighbours
    }

    fun link(nextCell: Cell?) {
        visited = true
        linkedCell = nextCell
        nextCell?.markAsVisited(this)
    }

    private fun markAsVisited(cell: Cell) {
        neighbours().forEach { neighbour ->
            if (neighbour == cell) neighbour.visited = true
        }
    }

    fun getCoords(cellSize: Float) : Coords {
        val halfDimen = cellSize / 2
        return Coords(
            column * cellSize,
            row * cellSize,
            (column * cellSize) + halfDimen,
            (row * cellSize) + halfDimen
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Cell)
            return false
        else {
            if (other.row == this.row
                && other.column == this.column)
                return true
        }
        return false
    }
}