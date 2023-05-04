package tic_tac_toe

import game_engine.{GamePiece, GameState, insideBoard}

object XOController {
  def control(state: GameState, move: List[String]): GameState = {
    if (move.head.length != 2)
      return state

    val x: Int = move.head(0) - '1'
    val y: Int = move.head(1) - 'a'

    if (!insideBoard(x, y, state.board) || state.board(x)(y) != null) {
      return state
    }

    // state.board = state.addMove(x, y, turn)
    val newBoard = addMove(x, y, state.board, state.player)

    //  true
    GameState(!state.player, newBoard)
  }

  // Apply the move on the board
  def addMove(row: Int, col: Int, board: Array[Array[GamePiece]], player: Boolean): Array[Array[GamePiece]] = {
    val newBoard = Array.tabulate(board.length, board.length)((x, y) =>
      if (x == row && y == col) GamePiece(if (player) "x" else "o", null)
      else board(x)(y))
    newBoard
  }
}
