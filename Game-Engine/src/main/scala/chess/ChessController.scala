package chess

import game_engine.Controller

object ChessController extends Controller[ChessBoard] {
  def control(state: ChessBoard, move: List[String], turn: Boolean): Boolean = {
    val color: String = if (turn) "white" else "black"

    // Wrong input format
    if (move.head.length != 2 || move(1).length != 2)
      return false

    val x1: Int = state.size - (move.head(0) - '1') - 1
    val y1: Int = move.head(1) - 'a'
    val x2: Int = state.size - (move(1)(0) - '1') - 1
    val y2: Int = move(1)(1) - 'a'

    // If out of bounds or cell is empty, return false
    if (!state.validMove((x1, y1), (x2, y2)) || state.board(y1)(x1) == null) {
      return false
    }

    // If chosen piece is not the player's piece
    if (state.board(y1)(x1).color != color)
      return false

    // If the attacked piece is one of the player's piece
    if (state.board(y2)(x2) != null)
      if (state.board(y2)(x2).color == color)
        return false

    // If the same cell is chosen as destination
    if (x1 == x2 && y1 == y2)
      return false

    if (!validMove(state.board, (x1, y1), (x2, y2)))
      return false

    state.board = state.addMove((x1, y1), (x2, y2))
    true

  }

  private def validMove(board: Array[Array[ChessPiece]], from: (Int, Int), to: (Int, Int)): Boolean = {
    board(from._2)(from._1).name match {
      case "rook" => checkRook(board, from._1, from._2, to._1, to._2)
      case "knight" => checkKnight(from._1, from._2, to._1, to._2)
      case "bishop" => checkBishop(board, from._1, from._2, to._1, to._2)
      case "queen" => checkQueen(board, from._1, from._2, to._1, to._2)
      case "king" => checkKing(from._1, from._2, to._1, to._2)
      case "pawn" => checkPawn(board, from._1, from._2, to._1, to._2)
      case _ => false
    }
  }

  private def checkRook(board: Array[Array[ChessPiece]], x1: Int, y1: Int, x2: Int, y2: Int): Boolean = {
    // If move is not straight
    if (x1 != x2 && y1 != y2)
      return false

    // Check in between cells are empty from start to end
    for (x <- Math.min(x1, x2) until Math.max(x1, x2))
      if (x != x1 && x != x2 && board(y1)(x) != null)
        return false

    for (y <- Math.min(y1, y2) until Math.max(y1, y2))
      if (y != y1 && y != y2 && board(y)(x1) != null)
        return false
    true
  }

  private def checkBishop(board: Array[Array[ChessPiece]], x1: Int, y1: Int, x2: Int, y2: Int): Boolean = {
    // If move is not diagonal, return false
    if (Math.abs(x1 - x2) != Math.abs(y1 - y2))
      return false

    val xDiff = if (x1 > x2) -1 else 1
    val yDiff = if (y1 > y2) -1 else 1
    var x = x1 + xDiff
    var y = y1 + yDiff

    // If the path is not empty, return false
    while (x != x2 && y != y2) {
      if (board(y)(x) != null)
        return false
      x = x + xDiff
      y = y + yDiff
    }
    true
  }

  private def checkKnight(x1: Int, y1: Int, x2: Int, y2: Int): Boolean = {
    Math.abs(x1 - x2) == 2 && Math.abs(y1 - y2) == 1 || Math.abs(x1 - x2) == 1 && Math.abs(y1 - y2) == 2
  }

  private def checkQueen(board: Array[Array[ChessPiece]], x1: Int, y1: Int, x2: Int, y2: Int): Boolean = {
    checkRook(board, x1, y1, x2, y2) || checkBishop(board, x2, y1, x2, y2)
  }

  private def checkKing(x1: Int, y1: Int, x2: Int, y2: Int): Boolean = {
    Math.abs(x1 - x2) <= 1 && Math.abs(y1 - y2) <= 1
  }

  private def checkPawn(board: Array[Array[ChessPiece]], x1: Int, y1: Int, x2: Int, y2: Int): Boolean = {
    // White pawn
    if (board(y1)(x1).color == "white") {
      // 2 steps
      if (x1 - x2 == 2)
        return x1 == 6 // Starting position should be second row from bottom

      if (x1 < x2 || x1 - x2 > 1) return false // Motion should be one cell upward
    }

    // Black pawn
    if (board(y1)(x1).color == "black") {
      // 2 steps
      if (x2 - x1 == 2)
        return x1 == 1 // Starting position should be second row from bottom

      if (x1 > x2 || x2 - x1 > 1) return false // Motion should be one cell downward
    }

    // If diagonal and killing move
    if (Math.abs(x1 - x2) == Math.abs(y1 - y2)) {
      return board(y1)(x1) != null && board(y2)(x2) != null
    }

    y1 == y2 // Straight motion in the same column
  }

}
