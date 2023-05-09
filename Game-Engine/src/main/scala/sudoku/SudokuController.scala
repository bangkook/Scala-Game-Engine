package sudoku

import game_engine.{GamePiece, GameState, insideBoard}

object SudokuController {
  def control(state: GameState, move: List[String]): GameState = {
    if (move.head.length != 2 || move(1).length != 1)
      return state

    val x: Int = move.head(0) - '1'
    val y: Int = move.head(1) - 'a'
    val z: Int = move(1)(0)-'0'
    val delete:Boolean= (z==0)


    //out of bound
    if (!insideBoard(x, y, state.board) ) {
      println("out of bound")
      return state
    }

    if(!delete ) {
      //breaks the rules
      if (!validAdd(state.board, x, y, z)) {
        println("invalid move")
        return state
      }else{
        val newBoard = addMove(x, y, z, state.board)
        return GameState(state.player, newBoard)
      }

    }else{// delete
      if(!validDelete(x,y,state.board)){
        println("can't delete this")
        return state
      }else{
        val newBoard = removeMove(x, y, state.board)
        return GameState(state.player, newBoard)
      }

    }


  }

  private def validAdd(board: Array[Array[GamePiece]], x:Int, y:Int, z:Int): Boolean = {
    if (!(z >= 1 && z <= 9)) {
      println("z out of bound")
      return false
    }
    //has a number
    if (board(x)(y).color == "init") {
      println("has an init number")
      return false
    }

    for(i<-0 until 9){
        if(board(x)(i).name==z.toString){
          println("same val in same col")
          return false
        }
        if(board(i)(y).name==z.toString) {
          println("same val in same row")
          return false
        }

    }
    val startrow=x-(x%3)
    val startcol=y-(y%3)
    for (i <- 0 until 3) {
      for (j <- 0 until 3) {
        if (board(i+startrow)(j+startcol).name == z.toString) {
          println("same val in same box")
          return false
        }
      }
    }
    true
  }

  def validDelete(x: Int, y: Int,board: Array[Array[GamePiece]]): Boolean = {
    board(x)(y).color != "init"
  }

  def addMove(x1: Int, y1: Int, z: Int,board: Array[Array[GamePiece]]): Array[Array[GamePiece]] = {
    val newBoard: Array[Array[GamePiece]] = Array.tabulate(board.length, board.length)((x, y) =>
      if (x==x1 && y==y1) GamePiece(z.toString, "free")
      else board(x)(y)
    )
    newBoard
  }

  def removeMove(x1: Int, y1: Int,board: Array[Array[GamePiece]]): Array[Array[GamePiece]] = {
    val newBoard: Array[Array[GamePiece]] = Array.tabulate(board.length, board.length)((x, y) =>
      if (x == x1 && y == y1) GamePiece("0", "free")
      else board(x)(y)
    )
    newBoard
  }
}
