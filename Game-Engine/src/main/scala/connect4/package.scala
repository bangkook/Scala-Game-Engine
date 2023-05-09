import game_engine.{GameState,GamePiece}
import utility.insideBoard
import javafx.scene.shape.Circle
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.{GridPane, StackPane}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.{Green, Red}
import scalafx.scene.text.{Font, FontWeight}
import scalafx.stage.Stage

package object connect4 {
  // Draws the game board and pieces given a game state
  def connect4Drawer(board: Array[Array[GamePiece]]): Unit = {
    val stage = new Stage()
    val grid = new GridPane()
    grid.padding = Insets(75, 500, 500, 100)

    grid.hgap = 2
    grid.vgap = 2

    for (x <- 0 until(board.length)) {
      for (y <- 0 until(board(0).length)) {
        setColumnLabels(y + 1, grid)
        val circle = new Circle(40)
        circle.setFill(Color.SkyBlue)
        circle.setStroke(Color.Black)
        circle.setStrokeWidth(2)

        val stack = new StackPane()
        stack.getChildren.add(circle)
        stack.setMinWidth(80)
        stack.setMinHeight(80)
        stack.setAlignment(Pos.Center)

        grid.add(stack, y + 1, x + 1)

        if (board(x)(y) != null) {
          if(board(x)(y).name ==  "1"){
            circle.setFill(Green)
          } else{
            circle.setFill(Red)
          }
        }
      }
    }
    stage.title = "Connect 4"
    stage.scene = new Scene(800, 700) {
      fill = Color.AliceBlue
      content = grid
    }
    stage.show()
  }

  // Validates the user input according to the rules of the game
  // Applies the user action and modifies the board accordingly
  def connect4Controller(state: GameState, move: List[String]): Array[Array[GamePiece]] = {
    if (move.head.length != 1)
      return state.board

    val y: Int = move.head(0) - '1'

    val x = valid(5,y,state.board)
    if (!insideBoard(x, y, state.board) || state.board(x)(y) != null) {
      return state.board
    }

    val newBoard = addMove(x, y, state.board, state.player)

    //  true
    newBoard
  }

  def addMove(row: Int, col: Int, board: Array[Array[GamePiece]], player: Boolean): Array[Array[GamePiece]] = {
    val newBoard = Array.tabulate(board.length, board(0).length)((x, y) =>
      if (x == row && y == col) GamePiece(if (player) "1" else "2", null)
      else board(x)(y))
    newBoard
  }

  def valid(i: Int, col: Int, board: Array[Array[GamePiece]]): Int = {
    //i start=5
    if (i > 5) {
      return -1
    }
    if (insideBoard(i, col, board) && board(i)(col) == null) {
      return i
    } else {
      return valid(i - 1, col, board)
    }

  }

  // Set columns characters
  def setColumnLabels(x: Int, grid: GridPane): Unit = {
    val row = ('1' + x - 1).toChar.toString
    val label = new Label(row)
    label.setFont(Font.font("Arial", FontWeight.Bold, 25))
    val stack = new StackPane()
    stack.setMinWidth(45)
    stack.setMinHeight(45)
    stack.setAlignment(Pos.Center)
    stack.getChildren.add(label)
    grid.add(stack, x, 7)
  }
}
