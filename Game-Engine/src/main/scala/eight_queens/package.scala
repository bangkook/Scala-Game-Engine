import game_engine.{GameState,GamePiece}
import utility.{getImage, insideBoard}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.{Background, BackgroundFill, GridPane, StackPane}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.Burlywood
import scalafx.scene.text.{Font, FontWeight}
import scalafx.stage.Stage

package object eight_queens {
  def queensDrawer(board: Array[Array[GamePiece]]): Unit = {
    val stage = new Stage()
    val grid = new GridPane()
    grid.padding = Insets(0, 10, 10, 5)

    val whiteBackground = new Background(Array(new BackgroundFill(Color.White, null, null)))
    val blackBackground = new Background(Array(new BackgroundFill(Color.Grey, null, null)))

    for (x <- board.indices) {
      setColumnLabels(x , grid)
      setRowLabels(board.length - x, grid)
      for (y <- 0 until(board(0).length)) {
        val stack = new StackPane()
        stack.setMinWidth(45)
        stack.setMinHeight(45)
        stack.setAlignment(Pos.Center)
        if(x != 8)
          stack.setBackground(if ((x + y) % 2 == 0) whiteBackground else blackBackground)
        else  stack.setStyle("-fx-background-color: transparent; -fx-border-color: black;")

        grid.add(stack, y + 1, x + 1)

        if (board(x)(y) != null)
          grid.add(getImage(board(x)(y).name, 45, 45), y + 1, x + 1)
      }
    }
    stage.title = "Eight Queens"
    stage.scene = new Scene(460, 480) {
      fill = Burlywood
      content = grid
    }
    stage.show()
  }

  def queensController(state: GameState, move: List[String]): GameState = { //check the input // a01
    if(move.head.length != 2)
      return state

    val size = state.board.length
    val to: Int = size - (move.head(0) - '1') - 2
    val queen: Int = move.head(1) - 'a'

    // If out of bounds or cell is empty, return false
    if (!insideBoard(to, queen, state.board) ) {
      println(1)
      return state
    }

    if(state.board(to)(queen) != null){ //piece
      val newBoard = deleteMove((to, queen), state.board);
      return GameState(!state.player, newBoard)
    }

    if (!validMove(state.board,  (to, queen))) {
      println(5)
      return state
    }
    val newBoard = addMove( (to, queen), state.board);
    GameState(!state.player, newBoard)
  }

  def deleteMove(to: (Int, Int), board: Array[Array[GamePiece]]): Array[Array[GamePiece]] = {
    val newBoard: Array[Array[GamePiece]] = Array.tabulate(board.length, board(0).length)((x, y) =>
      if (x == to._1 && y == to._2) null
      else if (x == 8 && y == to._2) GamePiece("queens", null)
      else board(x)(y)
    )
    newBoard
  }

  def addMove( to: (Int, Int), board: Array[Array[GamePiece]] ): Array[Array[GamePiece]] = {
    val newBoard: Array[Array[GamePiece]] = Array.tabulate(board.length, board(0).length)((x, y) =>
      if (x == to._1 && y == to._2) GamePiece("queens", null)
      else if (x == 8 && y == to._2) null
      else board(x)(y)
    )
    newBoard
  }

  def validMove(board: Array[Array[GamePiece]],  to: (Int, Int)): Boolean = {

    val x2 = to._1
    val y2 = to._2
    if(board(8)(y2) == null){
      return false
    }
    for (i <- 0 to 7) { //check row
      if (board(x2)(i) != null) {
        return false;
      }
    }

    // Check upper-left diagonal
    def checkUpperLeft(row: Int, col: Int): Boolean = {
      if ((col == 0 || row == 0) && board(row)(col) == null) true
      else if (board(row)(col) != null) false
      else checkUpperLeft(row - 1, col - 1)
    }

    // Check upper-right diagonal
    def checkUpperRight(row: Int, col: Int): Boolean = {
      if ((row == 0 || col == 7) && board(row)(col) == null) true
      else if (board(row)(col) != null) false
      else checkUpperRight(row - 1, col + 1)
    }

    // Check lower-right diagonal
    def checkLowerRight(row: Int, col: Int): Boolean = {
      if ((col == 7 || row == 7) && board(row)(col) == null) true
      else if (board(row)(col) != null) false
      else checkLowerRight(row + 1, col + 1)
    }

    // Check lower-left diagonal
    def checkLowerLeft(row: Int, col: Int): Boolean = {
      if ((col == 0 || row == 7) && board(row)(col) == null) true
      else if (board(row)(col) != null) false
      else checkLowerLeft(row + 1, col - 1)
    }
    return checkUpperLeft(x2, y2) && checkUpperRight(x2, y2) && checkLowerRight(x2, y2) && checkLowerLeft(x2, y2)
  }

  // Set columns characters
  def setColumnLabels(x: Int, grid: GridPane): Unit = {
    val col = ('a' + x - 1).toChar.toString
    val label = new Label(col)
    label.setFont(Font.font("Arial", FontWeight.Bold, 25))
    val stack = new StackPane()
    stack.setMinWidth(45)
    stack.setMinHeight(45)
    stack.setAlignment(Pos.Center)
    stack.getChildren.add(label)
    grid.add(stack, x, 0)
  }

  def setRowLabels(x: Int, grid: GridPane): Unit = {
    val row = ('0' + x - 1).toChar.toString
    val label = new Label(row)
    label.setFont(Font.font("Arial", FontWeight.Bold, 25))
    val stack = new StackPane()
    stack.setMinWidth(45)
    stack.setMinHeight(45)
    stack.setAlignment(Pos.Center)
    stack.getChildren.add(label)
    grid.add(stack, 0, 10 - x)
  }

}
