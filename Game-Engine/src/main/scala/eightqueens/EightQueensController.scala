package eightqueens

import chess.ChessController.validMove
import game_engine.{GamePiece, GameState, insideBoard}

import scala.annotation.tailrec

object EightQueensController {

  def control(state: GameState, move: List[String]): GameState = { //check the input // a01
    if(move.head.length != 2)
      return state

    val size = state.board.length
    val to: Int = size - (move.head(0) - '1') - 2
    val queen: Int = move.head(1) - 'a'

    // If out of bounds or cell is empty, return false
    if (!insideBoard(to, queen, state.board) ) {
      println(1)
      return state
    }

    if(state.board(to)(queen) != null){ //piece
      val newBoard = deleteMove((to, queen), state.board);
      return GameState(!state.player, newBoard)
    }

    if (!validMove(state.board,  (to, queen))) {
      println(5)
      return state
    }
    val newBoard = addMove( (to, queen), state.board);
    GameState(!state.player, newBoard)
  }
  def deleteMove(to: (Int, Int), board: Array[Array[GamePiece]]): Array[Array[GamePiece]] = {
    val newBoard: Array[Array[GamePiece]] = Array.tabulate(board.length, board(0).length)((x, y) =>
      if (x == to._1 && y == to._2) null
      else if (x == 8 && y == to._2) GamePiece("queens", null)
      else board(x)(y)
    )
    newBoard
  }
  def addMove( to: (Int, Int), board: Array[Array[GamePiece]] ): Array[Array[GamePiece]] = {
    val newBoard: Array[Array[GamePiece]] = Array.tabulate(board.length, board(0).length)((x, y) =>
      if (x == to._1 && y == to._2) GamePiece("queens", null)
      else if (x == 8 && y == to._2) null
      else board(x)(y)
    )
    newBoard
  }

  private def validMove(board: Array[Array[GamePiece]],  to: (Int, Int)): Boolean = {

    val x2 = to._1
    val y2 = to._2
    if(board(8)(y2) == null){
      return false
    }
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