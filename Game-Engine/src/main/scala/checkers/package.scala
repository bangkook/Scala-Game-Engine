import game_engine.{GamePiece, GameState, getImage, insideBoard}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Label}
import scalafx.scene.layout.{Background, BackgroundFill, GridPane, StackPane}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.LightGreen
import scalafx.scene.text.{Font, FontWeight}
import scalafx.stage.Stage

package object checkers {
  // Draws the game board and pieces given a game state
  def checkersDrawer(board: Array[Array[GamePiece]]): Unit = {
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
          grid.add(getImage(board(x)(y).color + "_" + board(x)(y).name, 45, 45), y + 1, x + 1)
      }
    }
    stage.title = "Checkers"
    stage.scene = new Scene(475, 450) {
      fill = LightGreen
      content = grid
    }
    stage.show()
  }

  // Validates the user input according to the rules of the game
  // Applies the user action and modifies the board accordingly
  def checkersController(state: GameState, move: List[String]): GameState = {
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

    if (!validMove(state.board, (x1, y1), (x2, y2),color)) {
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
      val eatX = (from._1 + to._1) / 2
      val eatY = (from._2 + to._2) / 2
      newBoard(eatX)(eatY) = null
    }
    newBoard
  }

  def validMove(board: Array[Array[GamePiece]], from: (Int, Int), to: (Int, Int), color: String): Boolean = {
    // if to is not null return false
    if(board(to._1)(to._2) != null) {
      return false
    }
    // if the move is not 1 or 2 break
    println(Math.abs(from._1 - to._1))
    if(Math.abs(from._1 - to._1) != 1 && Math.abs(from._1 - to._1) != 2) {
      return false
    }
    // white and black can move forward diagonally in their directions
    if(color == "white"){
      if (from._1 - to._1  != Math.abs(to._2 - from._2)) {
        return false
      }
    }else{ // color == "black
      if (to._1 - from._1 != Math.abs(from._2 - to._2))
        return false
    }
    val leftSide = check_grand_left(board, (from._1, from._2), (to._1, to._2),color)
    val rightSide = check_grand_right(board, (from._1, from._2), (to._1, to._2),color)
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

  def check_grand_left(board: Array[Array[GamePiece]], from:(Int,Int), to: (Int,Int),color: String): Int = {
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
