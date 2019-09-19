package grid

data class PngCoords(
    val x1: Int,
    val x2: Int,
    val y1: Int,
    val y2: Int,
    val xC: Int,
    val yC: Int
)

/*
x1/y1  _______  x2
      |       |
      |       |
      | xC/yC |
      |       |
   y2 |_______|

 */