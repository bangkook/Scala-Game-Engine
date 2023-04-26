package sudoku

class SudokuBoard {
  val size = 9
  var board: Array[Array[Int]] = Array.ofDim[Int](size, size)

    //initial
  board=SudokuGenerator.getSudoku(board)

  def validMove(x: Int, y: Int ,z:Int): Boolean = {
    println("x "+x+" y "+y+" z "+z)
    x >= 0 && x <= 8 && y >= 0 && y <= 8 && z >= 1 && z <= 9
  }

  def addMove(x: Int, y: Int, z:Int): Array[Array[Int]] = {
    board(x)(y) = z
    println("In add move")
    board
  }

  def removeMove(x: Int, y: Int): Array[Array[Int]] = {
    board(x)(y) = 0
    board
  }

}
