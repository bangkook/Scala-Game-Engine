class XOController {
  def control(state: XOBoard, move: List[Int], turn: Boolean): Boolean = {
    var board = state.board
    if (!state.validMove(move.head, move(1))) {
      return false
    }
    state.board = state.addMove(move.head, move(1), turn)
    true
  }
}
