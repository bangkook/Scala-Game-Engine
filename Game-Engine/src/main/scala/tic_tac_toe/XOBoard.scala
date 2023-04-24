package tic_tac_toe

class XOBoard {
  val size = 3
  var board: Array[Array[XOPiece]] = Array.ofDim[XOPiece](size, size)

  def validMove(x: Int, y: Int): Boolean = {
    x >= 0 && x <= 2 && y >= 0 && y <= 2 && board(x)(y) == null
  }

  def addMove(x: Int, y: Int, player: Boolean): Array[Array[XOPiece]] = {
    board(x)(y) = new XOPiece(player)
    board
  }

}
