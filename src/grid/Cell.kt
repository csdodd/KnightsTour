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
    }

    fun getPngCoords(cellSize: Int) : PngCoords {
        val halfDimen = cellSize / 2
        return PngCoords(
            column * cellSize,
            (column + 1) * cellSize,
            row * cellSize,
            (row + 1) * cellSize,
            (column * cellSize) + halfDimen,
            (row * cellSize) + halfDimen
        )
    }
}