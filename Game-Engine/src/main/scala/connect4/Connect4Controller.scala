package connect4

import game_engine.{GamePiece, GameState, insideBoard}

object Connect4Controller {
  def control(state: GameState, move: List[String]): GameState = {
    if (move.head.length != 1)
      return state

    val y: Int = move.head(0) - '1'

    val x = valid(y,state.board)
    if (!insideBoard(x, y, state.board) || state.board(x)(y) != null) {
      return state
    }

    val newBoard = addMove(x, y, state.board, state.player)

    //  true
    GameState(!state.player, newBoard)
  }
  def addMove(row: Int, col: Int, board: Array[Array[GamePiece]], player: Boolean): Array[Array[GamePiece]] = {
    val newBoard = Array.tabulate(board.length, board(0).length)((x, y) =>
      if (x == row && y == col) GamePiece(if (player) "1" else "2", null)
      else board(x)(y))
    newBoard
  }

  def valid(i: Int, col: Int, board: Array[Array[GamePiece]]): Int = {
    //i start=5
    if (i > 5) {
      return -1
    }
    if (insideBoard(i, col, board) && board(i)(col) == null) {
      return i
    } else {
      return valid(i - 1, col, board)
    }

  }
}
