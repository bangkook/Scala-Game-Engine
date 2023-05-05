package checkers

import game_engine.{GamePiece, GameState, insideBoard}


object CheckersController {
  def control(state: GameState, move: List[String]): GameState = {
    val color: String = if (state.player) "white" else "black"

    // Wrong input format
    if (move.head.length != 2 || move(1).length != 2)
      return state

    val size = state.board.length
    val x1: Int = size - (move.head(0) - '1') - 1
    val y1: Int = move.head(1) - 'a'
    val x2: Int = size - (move(1)(0) - '1') - 1
    val y2: Int = move(1)(1) - 'a'

    println(x1)

    println(y1)

    println(x2)
    println(y2)

    // If out of bounds or cell is empty, return false
    if (!insideBoard(x1, y1, state.board) || !insideBoard(x2, y2, state.board) || state.board(x1)(y1) == null) {
      println(1)
      return state
    }

    // If chosen piece is not the player's piece
    if (state.board(x1)(y1).color != color) {
      println(2)
      return state
    }

    // If the attacked piece is one of the player's piece
    if (state.board(x2)(y2) != null)
      if (state.board(x2)(y2).color == color) {
        println(3)
        return state
      }

    // If the same cell is chosen as destination
    if (x1 == x2 && y1 == y2) {
      println(4)
      return state
    }

    if (!validMove(state.board, (x1, y1), (x2, y2))) {
      println(5)
      return state
    }

    val newBoard = addMove((x1, y1), (x2, y2), state.board)
    GameState(!state.player, newBoard)
  }

  def addMove(from: (Int, Int), to: (Int, Int), board: Array[Array[GamePiece]] ): Array[Array[GamePiece]] = {
    val color = board(from._1)(from._2).color
    val name = board(from._1)(from._2).name
    val newBoard: Array[Array[GamePiece]] = Array.tabulate(board.length, board.length)((x, y) =>
      if (x == to._1 && y == to._2) GamePiece(name, color)
      else if (x == from._1 && y == from._2) null
      else board(x)(y)
    )
    newBoard
  }

  private def validMove(board: Array[Array[GamePiece]], from: (Int, Int), to: (Int, Int)): Boolean = {
    return true
  }
//  private def check_grand_left(board: Array[Array[GamePiece]], from:(Int,Int), to: (Int,Int)): Boolean = {
//    return true
//  }

}
