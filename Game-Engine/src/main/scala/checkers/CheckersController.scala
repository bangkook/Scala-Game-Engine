package checkers

import game_engine.{GamePiece, GameState, insideBoard}
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

object CheckersController {
  def control(state: GameState, move: List[String]): GameState = {
    val color: String = if (state.player) "white" else "black"

    // Wrong input format
    if (move.head.length != 2 || move(1).length != 2)
      return state

    val size = state.board.length
    val x1: Int = size - (move.head(0) - '1') - 1
    val y1: Int = move.head(1) - 'a'
    val x2: Int = size - (move(1)(0) - '1') - 1
    val y2: Int = move(1)(1) - 'a'

    println("x1 = "+ x1)

    println("y1 = "+y1)

    println("x2 = "+x2)
    println("y2 = "+y2)

    // If out of bounds or cell is empty, return false
    if (!insideBoard(x1, y1, state.board) || !insideBoard(x2, y2, state.board) || state.board(x1)(y1) == null) {
      println(1)
      return state
    }

    // If chosen piece is not the player's piece
    if (state.board(x1)(y1).color != color) {
      println(2)
      return state
    }

    // If the attacked piece is one of the player's piece
    if (state.board(x2)(y2) != null)
      if (state.board(x2)(y2).color == color) {
        println(3)
        return state
      }

    // If the same cell is chosen as destination
    if (x1 == x2 && y1 == y2) {
      println(4)
      return state
    }

    if (!validMove(state.board, (x1, y1), (x2, y2),color)) {
      println(5)
      return state
    }

    val newBoard = addMove((x1, y1), (x2, y2), state.board)
    GameState(!state.player, newBoard)
  }

  def addMove(from: (Int, Int), to: (Int, Int), board: Array[Array[GamePiece]] ): Array[Array[GamePiece]] = {
    val color = board(from._1)(from._2).color
    val name = board(from._1)(from._2).name


    val newBoard: Array[Array[GamePiece]] = Array.tabulate(board.length, board.length)((x, y) =>
      if (x == to._1 && y == to._2) GamePiece(name, color)
      else if (x == from._1 && y == from._2) null
      else board(x)(y)
    )
    if(Math.abs(from._1 - to._1) == 2){ //eating
      val eatx = (from._1 + to._1) / 2
      val eaty = (from._2 + to._2) / 2
      println("eatx = " +eatx+ " , eaty = "+ eaty)
      newBoard(eatx)(eaty) = null
    }
    newBoard
  }



  private def validMove(board: Array[Array[GamePiece]], from: (Int, Int), to: (Int, Int), color: String): Boolean = {
    // if to is not null return false
    if(board(to._1)(to._2) != null) {
      println("Heree in 85")
      return false
    }
    // if the move is not 1 or 2 break
    println(Math.abs(from._1 - to._1))
    if(Math.abs(from._1 - to._1) != 1 && Math.abs(from._1 - to._1) != 2) {
      println("Heree in 90")
      return false
    }
    // white and black can move forward diagonally in their directions
    if(color == "white"){
      if (from._1 - to._1  != Math.abs(to._2 - from._2)) {
        println("in line 92")
        return false
      }
    }else{ // color == "black
      if (to._1 - from._1 != Math.abs(from._2 - to._2))
        return false
    }
    val leftSide = check_grand_left(board, (from._1, from._2), (to._1, to._2),color)
    val rightSide = check_grand_right(board, (from._1, from._2), (to._1, to._2),color)
    println("rightSide = "+ rightSide+ " , leftSide = "+ leftSide)
    if(rightSide == 1){
      return true
    }else if(rightSide == 2 || rightSide == 0){
      if (leftSide == 1) {
        return true
      } else if(leftSide == 2 || rightSide == 2){
        // alert you can attack
        val alert = new Alert(AlertType.Error)
        alert.setContentText("You can attack")
        alert.show()
        return false
      }else if(leftSide ==  0){
        if( Math.abs(from._1 - to._1) == 2) return false
      }
    }
    true
  }
  private def check_grand_left(board: Array[Array[GamePiece]], from:(Int,Int), to: (Int,Int),color: String): Int = {
    val fromRow = from._1
    val fromCol = from._2
    val toRow = to._1
    val toCol = to._2
    val sign = if(color == "white") -1 else 1
    println("board.length = "+ board.length)
    if(fromRow + 2 * sign > board.length || fromCol-2 < 0) return 0
    val prey = board(fromRow + 1*sign)(fromCol-1)
    if(prey == null) return 0
    val attackedPiece = if (prey.color == "white") -1 else 1
    if(attackedPiece == -1*sign && board(fromRow+2*sign)(fromCol-2) == null){
      if(toRow == fromRow + 2 * sign && toCol == fromCol - 2){
        //eating my opponent
        // board(fromRow+1*sign)(fromCol-1) = null
        return 1
      }else{
        //User didn't input this path in grand_left
        return 2
      }
    }
    0

  }
  def check_grand_right(board: Array[Array[GamePiece]], from:(Int,Int), to: (Int,Int),color: String): Int = {
    val fromRow = from._1
    val fromCol = from._2
    val toRow = to._1
    val toCol = to._2

    val sign = if(color == "white") -1 else 1

    if(fromRow + 2 * sign > board.length ||(fromCol + 2) > board.length) return 0
    val prey = board(fromRow + 1*sign)(fromCol+1)
    if(prey == null) return 0
    val attackedPiece = if (prey.color == "white") -1 else 1
    if(attackedPiece == -1*sign && board(fromRow+2*sign)(fromCol+2) == null){
      if(toRow == fromRow + 2 * sign && toCol == fromCol + 2){
        //eating my opponent
        // board(fromRow+1*sign)(fromCol+1) = null
        return 1
      }else{
        //User didn't input this path in grand_left
        return 2
      }
    }
    0
  }
}
