import game_engine.{GamePiece, GameState}
import utility.{getImage, insideBoard}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.{Background, BackgroundFill, GridPane, StackPane}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.Burlywood
import scalafx.scene.text.{Font, FontWeight}
import scalafx.stage.Stage

import scala.annotation.tailrec

package object chess {
  // Draws the game board and pieces given a game state
  def chessDrawer(board: Array[Array[GamePiece]]): Unit = {
    val stage = new Stage()
    val grid = new GridPane()
    grid.padding = Insets(0, 10, 10, 5)

    val whiteBackground = new Background(Array(new BackgroundFill(Color.White, null, null)))
    val blackBackground = new Background(Array(new BackgroundFill(Color.Grey, null, null)))

    for (x <- board.indices) {
      setColumnLabels(x + 1, grid)
      setRowLabels(board.length - x, grid)
      for (y <- board.indices) {
        val stack = new StackPane()
        stack.setMinWidth(45)
        stack.setMinHeight(45)
        stack.setAlignment(Pos.Center)
        stack.setBackground(if ((x + y) % 2 == 0) whiteBackground else blackBackground)

        grid.add(stack, y + 1, x + 1)

        if (board(x)(y) != null)
          grid.add(getImage(board(x)(y).color + "-" + board(x)(y).name, 45, 45), y + 1, x + 1)
      }
    }
    stage.title = "Chess"
    stage.scene = new Scene(475, 450) {
      fill = Burlywood
      content = grid
    }
    stage.show()
  }

  // Validates the user input according to the rules of the game
  // Applies the user action and modifies the board accordingly
  def chessController(state: GameState, move: List[String]): GameState = {
    val color: String = if (state.player) "white" else "black"

    // Wrong input format
    if (move.head.length != 2 || move(1).length != 2)
      return state

    val size = state.board.length
    val x1: Int = size - (move.head(0) - '1') - 1
    val y1: Int = move.head(1) - 'a'
    val x2: Int = size - (move(1)(0) - '1') - 1
    val y2: Int = move(1)(1) - 'a'

    // If out of bounds or cell is empty, return false
    if (!insideBoard(x1, y1, state.board) || !insideBoard(x2, y2, state.board) || state.board(x1)(y1) == null) {
      println(1)
      return state
    }

    // If chosen piece is not the player's piece
    if (state.board(x1)(y1).color != color) {
      return state
    }

    // If the attacked piece is one of the player's piece
    if (state.board(x2)(y2) != null)
      if (state.board(x2)(y2).color == color) {
        return state
      }

    // If the same cell is chosen as destination
    if (x1 == x2 && y1 == y2) {
      return state
    }

    if (!validMove(state.board, (x1, y1), (x2, y2))) {
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
    newBoard
  }

   def validMove(board: Array[Array[GamePiece]], from: (Int, Int), to: (Int, Int)): Boolean = {
    board(from._1)(from._2).name match {
      case "rook" => checkRook(board, from._1, from._2, to._1, to._2)
      case "knight" => checkKnight(from._1, from._2, to._1, to._2)
      case "bishop" => checkBishop(board, from._1, from._2, to._1, to._2)
      case "queen" => checkQueen(board, from._1, from._2, to._1, to._2)
      case "king" => checkKing(from._1, from._2, to._1, to._2)
      case "pawn" => checkPawn(board, from._1, from._2, to._1, to._2)
      case _ => false
    }
  }

   def checkRook(board: Array[Array[GamePiece]], x1: Int, y1: Int, x2: Int, y2: Int): Boolean = {
    // If move is not straight
    if (x1 != x2 && y1 != y2)
      return false

    // Check in between cells are empty from start to end
    for (x <- Math.min(x1, x2) until Math.max(x1, x2))
      if (x != x1 && x != x2 && board(x)(y1) != null)
        return false

    for (y <- Math.min(y1, y2) until Math.max(y1, y2))
      if (y != y1 && y != y2 && board(x1)(y) != null)
        return false
    true
  }

   def checkBishop(board: Array[Array[GamePiece]], x1: Int, y1: Int, x2: Int, y2: Int): Boolean = {
    // If move is not diagonal, return false
    if (Math.abs(x1 - x2) != Math.abs(y1 - y2))
      return false

    val xDiff = if (x1 > x2) -1 else 1
    val yDiff = if (y1 > y2) -1 else 1

    @tailrec // Check path is empty
    def loop(x: Int, y: Int): Boolean = {
      if (x == x2 || y == y2)
        return true
      if (board(x)(y) != null)
        return false
      loop(x + xDiff, y + yDiff)
    }

    loop(x1 + xDiff, y1 + yDiff)
  }

   def checkKnight(x1: Int, y1: Int, x2: Int, y2: Int): Boolean = {
    Math.abs(x1 - x2) == 2 && Math.abs(y1 - y2) == 1 || Math.abs(x1 - x2) == 1 && Math.abs(y1 - y2) == 2
  }

   def checkQueen(board: Array[Array[GamePiece]], x1: Int, y1: Int, x2: Int, y2: Int): Boolean = {
    checkRook(board, x1, y1, x2, y2) || checkBishop(board, x2, y1, x2, y2)
  }

   def checkKing(x1: Int, y1: Int, x2: Int, y2: Int): Boolean = {
    Math.abs(x1 - x2) <= 1 && Math.abs(y1 - y2) <= 1
  }

   def checkPawn(board: Array[Array[GamePiece]], x1: Int, y1: Int, x2: Int, y2: Int): Boolean = {
    // White pawn
    if (board(x1)(y1).color == "white") {
      // 2 steps
      if (x1 - x2 == 2)
        return x1 == 6 // Starting position should be second row from bottom

      if (x1 < x2 || x1 - x2 > 1) return false // Motion should be one cell upward
    }

    // Black pawn
    if (board(x1)(y1).color == "black") {
      // 2 steps
      if (x2 - x1 == 2)
        return x1 == 1 // Starting position should be second row from bottom

      if (x1 > x2 || x2 - x1 > 1) return false // Motion should be one cell downward
    }

    // If diagonal and killing move
    if (Math.abs(x1 - x2) == Math.abs(y1 - y2)) {
      return board(x1)(y1) != null && board(x2)(y2) != null
    }

    y1 == y2 // Straight motion in the same column
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
    val row = ('1' + x - 1).toChar.toString
    val label = new Label(row)
    label.setFont(Font.font("Arial", FontWeight.Bold, 25))
    val stack = new StackPane()
    stack.setMinWidth(45)
    stack.setMinHeight(45)
    stack.setAlignment(Pos.Center)
    stack.getChildren.add(label)
    grid.add(stack, 0, 9 - x)
  }

}
