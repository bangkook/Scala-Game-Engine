package chess

class ChessBoard(board: Array[Array[ChessPiece]]) {
  val size: Int = {
    board.length
  }

  val black: String = "black"
  val white: String = "white"
  val pawn: String = "pawn"
  val chessPieces: Array[String] = Array("rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook")

  def initializeAll: ChessBoard = {
    val newBoard = Array.tabulate(size, size)((x, y) => {
      if (x == 0) new ChessPiece(black, chessPieces(y))
      else if (x == 1) new ChessPiece(black, pawn)
      else if (x == size - 1) new ChessPiece(white, chessPieces(y))
      else if (x == size - 2) new ChessPiece(white, pawn)
      else null
    })
    new ChessBoard(newBoard)
  }

  def validMove(from: (Int, Int), to: (Int, Int)): Boolean = {
    from._1 >= 0 && from._1 < size && from._2 >= 0 && from._2 < size &&
      to._1 >= 0 && to._1 < size && to._2 >= 0 && to._2 < size
  }

  def get(x: Int, y: Int): ChessPiece = {
    board(x)(y)
  }

  def addMove(from: (Int, Int), to: (Int, Int)): Array[Array[ChessPiece]] = {
    val color = this.get(from._1, from._2).color
    val name = this.get(from._1, from._2).name
    val newBoard: Array[Array[ChessPiece]] = Array.tabulate(size, size)((x, y) =>
      if (x == to._1 && y == to._2) new ChessPiece(color, name)
      else if (x == from._1 && y == from._2) null
      else this.get(x, y)
    )
    newBoard
  }
}

