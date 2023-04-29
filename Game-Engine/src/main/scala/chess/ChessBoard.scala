package chess

import scala.annotation.tailrec

class ChessBoard(board: Array[Array[ChessPiece]]) {
  val size: Int = {
    board.length
  }

  val black: String = "black"
  val white: String = "white"
  val pawn: String = "pawn"
  val chessPieces: Array[String] = Array("rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook")

  def initializePos(y: Int): Unit = {
    board(y)(0) = new ChessPiece(black, chessPieces(y))
    board(y)(size - 1) = new ChessPiece(white, chessPieces(y))

    board(y)(1) = new ChessPiece(black, pawn)
    board(y)(size - 2) = new ChessPiece(white, pawn)
  }

  // loop n times applying function fn
  @tailrec
  final def loop(fn: Int => Unit, i: Int, n: Int): Unit = {
    if (i == n)
      return
    fn(i)
    loop(fn, i + 1, n)
  }

  loop(initializePos, 0, size)

  def validMove(from: (Int, Int), to: (Int, Int)): Boolean = {
    from._1 >= 0 && from._1 < size && from._2 >= 0 && from._2 < size &&
      to._1 >= 0 && to._1 < size && to._2 >= 0 && to._2 < size
  }

  def get(x: Int, y: Int): ChessPiece = {
    board(x)(y)
  }

  def addMove(from: (Int, Int), to: (Int, Int)): Array[Array[ChessPiece]] = {
    val color = this.get(from._2, from._1).color
    val name = this.get(from._2, from._1).name
    val newBoard = Array.tabulate(size, size)((x, y) =>
      if (x == to._2 && y == to._1) new ChessPiece(color, name)
      else if (x == from._2 && y == from._1) {
        println("Here")
        new ChessPiece(black, name)
      }
      else this.get(x, y))
    newBoard
  }
}
