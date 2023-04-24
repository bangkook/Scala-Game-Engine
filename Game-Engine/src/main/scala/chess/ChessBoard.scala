package chess

class ChessBoard {
  val size = 8
  val black: String = "black"
  val white: String = "white"
  val pawn: String = "pawn"
  var board: Array[Array[ChessPiece]] = Array.ofDim[ChessPiece](size, size)
  val chessPieces = List("rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook")

  for (y <- 0 until size) {
    board(y)(0) = new ChessPiece(black, chessPieces(y))
    board(y)(size - 1) = new ChessPiece(white, chessPieces(y))

    board(y)(1) = new ChessPiece(black, pawn)
    board(y)(size - 2) = new ChessPiece(white, pawn)
  }

  def validMove(from: (Int, Int), to: (Int, Int)): Boolean = {
    from._1 >= 0 && from._1 < size && from._2 >= 0 && from._2 < size &&
      to._1 >= 0 && to._1 < size && to._2 >= 0 && to._2 < size
  }

  def addMove(from: (Int, Int), to: (Int, Int)): Array[Array[ChessPiece]] = {
    //board(to._2)(to._1) = board(from._2)(from._1)
    board(from._2)(from._1) = board(to._2)(to._1)
    board(to._2)(to._1) = null
    board
  }

}
