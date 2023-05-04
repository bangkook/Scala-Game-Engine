package checkers


class CheckersBoard {
  val size = 8
  val black: String = "black"
  val white: String = "white"
  var board: Array[Array[Int]] = Array.ofDim[Int](size, size)
  board = Array(
    Array(0,1,0,1, 0, 1, 0, 1),
    Array(1, 0, 1, 0, 1, 0, 1, 0),
    Array(0, 1, 0, 1, 0, 1, 0, 1),
    Array(0, 0, 0, 0, 0, 0, 0, 0),
    Array(0, 0, 0, 0, 0, 0, 0, 0),
    Array(-1, 0, -1, 0, -1, 0, -1, 0),
    Array(0, -1, 0, -1, 0, -1, 0, -1),
    Array(-1, 0, -1, 0, -1, 0, -1, 0),
  )

  def validMove(from: (Int, Int), to: (Int, Int)): Boolean = {
    from._1 >= 0 && from._1 < size && from._2 >= 0 && from._2 < size &&
      to._1 >= 0 && to._1 < size && to._2 >= 0 && to._2 < size
  }

  def addMove(from: (Int, Int), to: (Int, Int)): Array[Array[Int]] = {
    board(to._2)(to._1) = board(from._2)(from._1)
    board(from._2)(from._1) = 0
    board
  }
}
