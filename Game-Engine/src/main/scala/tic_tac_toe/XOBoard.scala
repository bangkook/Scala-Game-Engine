package tic_tac_toe

class XOBoard(board: Array[Array[XOPiece]]) {
  // val size = 3
  val size: Int = {
    board.length
  }

  def validMove(x: Int, y: Int): Boolean = {
    x >= 0 && x < size && y >= 0 && y < size
  }

  def get(x: Int, y: Int): XOPiece = {
    board(x)(y)
  }

  def addMove(row: Int, col: Int, player: Boolean): Array[Array[XOPiece]] = {
    val newBoard = Array.tabulate(size, size)((x, y) =>
      if (x == col && y == row) new XOPiece(player)
      else this.get(x, y))

    newBoard
  }

}
