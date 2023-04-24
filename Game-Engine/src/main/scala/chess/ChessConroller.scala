package chess

object ChessConroller {
  def control(state: ChessBoard, move: List[String], turn: Boolean): Boolean = {
    var color: String = if (turn) "white" else "black"

    val x1: Int = state.size - (move.head(0) - '1') - 1
    val y1: Int = move.head(1) - 'a'
    val x2: Int = state.size - (move(1)(0) - '1') - 1
    val y2: Int = move(1)(1) - 'a'
    println(x1)
    println(y1)
    println(x2)
    println(y2)

    state.board = state.addMove((x1, y1), (x2, y2))
    true

  }

}
