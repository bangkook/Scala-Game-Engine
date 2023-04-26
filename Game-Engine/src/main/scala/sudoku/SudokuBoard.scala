package sudoku

class SudokuBoard {
  val size = 9
  var board: Array[Array[Int]] = Array.ofDim[Int](size, size)
  var intialBoard: Array[Array[Int]] = Array.ofDim[Int](size, size)
  //initial
  SudokuGenerator.getSudoku(intialBoard)
  board=intialBoard.map(_.clone)

  def validPlace(x: Int, y: Int): Boolean = {
    println("x "+x+" y "+y)
    x >= 0 && x <= 8 && y >= 0 && y <= 8
  }

  def validDelete(x: Int, y: Int): Boolean = {
    println("In validDelete "+"x " + x + " y " + y)
    intialBoard(x)(y)==0
  }

  def addMove(x: Int, y: Int, z:Int): Array[Array[Int]] = {
    board(x)(y) = z
    println("In add move")
    board
  }

  def removeMove(x: Int, y: Int): Array[Array[Int]] = {
    println("In removeMove")
    board(x)(y) = 0
    board
  }

}
