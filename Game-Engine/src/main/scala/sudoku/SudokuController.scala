package sudoku

import game_engine.Controller

object SudokuController extends Controller[SudokuBoard]{
  override def control(state: SudokuBoard, move: List[String], turn: Boolean): Boolean = {
    val x: Int = move(1)(0) - '1'
    val y: Int = move(1)(1) - 'a'
    val z: Int = if(move.head!="") move.head(0)-'0' else -1
    val delete:Boolean= (move.head=="")

    //out of bound
    if (!state.validPlace(x,y) ) {
      println("out of bound")
      return false
    }

    if(!delete ) {
      //breaks the rules
      if (!validAdd(state.board, x, y, z)) {
        println("invalid move")
        return false
      }
      println("before add move")
      state.board = state.addMove(x, y, z);
    }else{// delete
      if(!state.validDelete(x,y)){
        println("can't delete this")
        return false
      }
      state.board = state.removeMove(x, y);
    }

    true
  }

  private def validAdd(board: Array[Array[Int]], x:Int, y:Int, z:Int): Boolean = {
    if (!(z >= 1 && z <= 9)) {
      println("z out of bound")
      return false
    }
    //has a number
    if (board(x)(y) != 0) {
      println("has a number")
      return false
    }

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
