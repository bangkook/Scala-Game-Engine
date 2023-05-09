package eightqueens

import chess.ChessController.validMove
import game_engine.{GamePiece, GameState, insideBoard}

import scala.annotation.tailrec

object EightQueensController {

  def control(state: GameState, move: List[String]): GameState = { //check the input // a01
    if(move.head.length != 3)
      return state

    val size = state.board.length
    val queen: Int = move.head(0) - 'a'
    val from: Int = size - (move.head(1) - '1') - 2
    val to = size - (move.head(2)- '1') - 2
    println(s"Queen: $queen, From: $from, To: $to")

    // If out of bounds or cell is empty, return false
    if (!insideBoard(from, queen, state.board) || !insideBoard(to, queen, state.board) || state.board(from)(queen) == null) {
      println(1)
      return state
    }
    if (!validMove(state.board, (from, queen), (to, queen))) {
      println(5)
      return state
    }
    val newBoard = addMove((from, queen), (to, queen), state.board)
    GameState(!state.player, newBoard)
  }

  def addMove(from: (Int, Int), to: (Int, Int), board: Array[Array[GamePiece]] ): Array[Array[GamePiece]] = {
    val name = board(from._1)(from._2).name
    print(name)
    val newBoard: Array[Array[GamePiece]] = Array.tabulate(board.length, board(0).length)((x, y) =>
      if (x == to._1 && y == to._2) GamePiece(name, null)
      else if (x == from._1 && y == from._2) null
      else board(x)(y)
    )
    newBoard
  }

  private def validMove(board: Array[Array[GamePiece]], from: (Int, Int), to: (Int, Int)): Boolean = {
    if (to._1 == 8) {
      return true;
    }
    val x2 = to._1
    val y2 = to._2
    for (i <- 0 to 7) { //check row
      if (board(x2)(i) != null) {
        return false;
      }
    }
    // Check upper-left diagonal
    def checkUpperLeft(row: Int, col: Int): Boolean = {
      if ((col == 0 || row == 0) && board(row)(col) == null) true
      else if (board(row)(col) != null) false
      else checkUpperLeft(row - 1, col - 1)
    }
    // Check upper-right diagonal
    def checkUpperRight(row: Int, col: Int): Boolean = {
      if ((row == 0 || col == 7) && board(row)(col) == null) true
      else if (board(row)(col) != null) false
      else checkUpperRight(row - 1, col + 1)
    }
    // Check lower-right diagonal
    def checkLowerRight(row: Int, col: Int): Boolean = {
      if ((col == 7 || row == 7) && board(row)(col) == null) true
      else if (board(row)(col) != null) false
      else checkLowerRight(row + 1, col + 1)
    }
    // Check lower-left diagonal
    def checkLowerLeft(row: Int, col: Int): Boolean = {
      if ((col == 0 || row == 7) && board(row)(col) == null) true
      else if (board(row)(col) != null) false
      else checkLowerLeft(row + 1, col - 1)
    }
    return checkUpperLeft(x2, y2) && checkUpperRight(x2, y2) && checkLowerRight(x2, y2) && checkLowerLeft(x2, y2)
  }
}



