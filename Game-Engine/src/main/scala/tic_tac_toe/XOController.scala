package tic_tac_toe

import game_engine.GameState

object XOController {
  def control(state: GameState, move: List[String]): GameState = {
    if (move.head.length != 2)
      return state

    val x: Int = move.head(0) - '1'
    val y: Int = move.head(1) - 'a'

    if (!XOBoard.validMove(x, y, state.board) || state.board(x)(y) != null) {
      return state
    }

    // state.board = state.addMove(x, y, turn)
    val newBoard = XOBoard.addMove(x, y, state.board, state.player)

    //  true
    GameState(!state.player, newBoard)
  }
}
