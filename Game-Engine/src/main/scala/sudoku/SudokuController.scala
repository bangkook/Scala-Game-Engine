package sudoku

import game_engine.Controller

object SudokuController extends Controller[SudokuBoard]{
  override def control(state: SudokuBoard, move: List[String], turn: Boolean): Boolean = {
    val x: Int = move(1)(0) - '1'
    val y: Int = move(1)(1) - 'a'
    val z: Int = move.head(0)-'1'+1

    //out of bound
    if (!state.validMove(x,y,z) ) {
      println("out of bound")
      return false
    }

    //has a number
    if( state.board(x)(y) !=0) {
      println("has a number")
      return false
    }

    //breaks the rules
    if(!validMove(state.board, x,y,z)) {
      println("invalid move")
      return false
    }
    println("before add move")
    state.board=state.addMove(x,y,z);
    true
  }

  private def validMove(board: Array[Array[Int]], x:Int, y:Int, z:Int): Boolean = {
    for(i<-0 until board.size){
      for(j<-0 until board.size){
        if(board(x)(i)==z)return false
        if(board(i)(y)==z)return false
      }
    }
    val startrow=x-(x%3)
    val startcol=y-(y%3)
    for (i <- 0 until 3) {
      for (j <- 0 until 3) {
        if (board(i+startrow)(j+startcol) == z) return false
      }
    }
    true
  }
}
