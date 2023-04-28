package tic_tac_toe

class XOBoard {
  val size = 3
  var board: Array[Array[XOPiece]] = Array.ofDim[XOPiece](size, size)

  def validMove(x: Int, y: Int): Boolean = {
    x >= 0 && x < size && y >= 0 && y < size
  }

  def addMove(x: Int, y: Int, player: Boolean): Array[Array[XOPiece]] = {
    board(y)(x) = new XOPiece(player)
    board
  }

}
