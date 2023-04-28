package checkers


object CheckersController {
  def control(state: CheckersBoard, move: List[String], turn: Boolean):Boolean ={
    val color = if(turn) -1 else 1
    // Wrong input format
    if (move.head.length != 2 || move(1).length != 2)
      return false
    // input fields --> ???
    val fromRow: Int = state.size - (move.head(0) - '1') - 1
    val fromCol: Int = move.head(1) - 'a'
    val toRow: Int = state.size - (move(1)(0) - '1') - 1
    val toCol: Int = move(1)(1) - 'a'

    // If out of bounds or cell is empty, return false
    if (!state.validMove((fromRow, fromCol), (toRow, toCol)) || state.board(fromRow)(fromCol) == 0) {
      return false
    }
    // If chosen piece is not the player's piece
    if (state.board(fromRow)(fromCol) != color) return false

    // If the attacked piece is one of the player's piece
    if (state.board(toRow)(toCol) == color) return false

    // If the same cell is chosen as destination
    if(fromRow == toRow && fromCol == toCol) return false

    if (!isValid(state.board, (fromRow, fromCol), (toRow, toCol), color))
      return false

    state.board = state.addMove((fromRow, fromCol), (toRow, toCol))
    true
  }

  private def check_grand_right(board: Array[Array[Int]], from: (Int, Int), to: (Int, Int), sign: Int): Int = {
    val fromRow = from._1
    val fromCol = from._1
    val toRow = to._1
    val toCol = to._2
    if(board(fromRow + 1 * sign)(fromCol + 1) == -1 * sign && (board(toRow + 2 * sign)(toCol + 2) == 0)){
      if(toRow == fromRow + 2 * sign && toCol == fromCol + 2){
        board(fromRow + 1 * sign)(fromCol + 1) = 0
        1
      } else 2

    } else 0
  }

  private def check_grand_left(board: Array[Array[Int]], from: (Int, Int), to: (Int, Int), sign: Int): Int = {
    val fromRow = from._1
    val fromCol = from._1
    val toRow = to._1
    val toCol = to._2
    if(board(fromRow + 1 * sign)(fromCol - 1) == -1 * sign && (board(toRow + 2 * sign)(toCol - 2) == 0)){
      if(toRow == fromRow + 2 * sign && toCol == fromCol - 2){
        board(fromRow + 1 * sign)(fromCol - 1) = 0
        1
      } else 2

    } else 0
  }

  //private def check_move

  private def isValid(board: Array[Array[Int]], from: (Int, Int), to: (Int, Int), color: Int): Boolean = {
//    if(board(from._1)(from._2) )
    return true
  }


}
