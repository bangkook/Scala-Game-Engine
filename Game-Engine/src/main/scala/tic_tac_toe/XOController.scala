package tic_tac_toe

class XOController {
  def control(state: XOBoard, move: List[String], turn: Boolean): Boolean = {
    if (move.head.length != 2)
      return false

    val x: Int = move.head(0) - '1'
    val y: Int = move.head(1) - 'a'

    if (!state.validMove(x, y)) {
      return false
    }

    state.board = state.addMove(x, y, turn)
    true
  }
}
