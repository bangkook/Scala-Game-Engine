package tic_tac_toe

import game_engine.Controller

object XOController extends Controller[XOBoard] {
  def control(state: XOBoard, move: List[String], turn: Boolean): Boolean = {
    if (move.head.length != 2)
      return false

    val x: Int = move.head(0) - '1'
    val y: Int = move.head(1) - 'a'

    if (!state.validMove(x, y) || state.board(x)(y) != null) {
      return false
    }

    state.board = state.addMove(x, y, turn)
    true
  }
}
