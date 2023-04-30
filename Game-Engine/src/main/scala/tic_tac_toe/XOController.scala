package tic_tac_toe

import game_engine.Controller

object XOController extends Controller[XOBoard] {
  def control(state: XOBoard, move: List[String], turn: Boolean): XOBoard = {
    if (move.head.length != 2)
      return state

    val x: Int = move.head(0) - '1'
    val y: Int = move.head(1) - 'a'

    if (!state.validMove(x, y) || state.get(x, y) != null) {
      return state
    }

    // state.board = state.addMove(x, y, turn)
    val newBoard = state.addMove(x, y, turn)

    //  true
    new XOBoard(newBoard)
  }
}
