package tic_tac_toe

import game_engine.GamePiece

object XOBoard {
  // val size = 3
  // val size: Int = {
  // board.length
  //}

  // Check move is within bounds
  def validMove(x: Int, y: Int, board: Array[Array[GamePiece]]): Boolean = {
    x >= 0 && x < board.length && y >= 0 && y < board.length
  }

  //def get(x: Int, y: Int): XOPiece = {
  //board(x)(y)
  // }

  // Apply the move on the board
  def addMove(row: Int, col: Int, board: Array[Array[GamePiece]], player: Boolean): Array[Array[GamePiece]] = {
    val newBoard = Array.tabulate(board.length, board.length)((x, y) =>
      if (x == row && y == col) GamePiece(if (player) "x" else "o", null)
      else board(x)(y))
    newBoard
  }

}
