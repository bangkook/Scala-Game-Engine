import game_engine.{GameState,GamePiece}
import utility.{insideBoard, getImage}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._
import scalafx.scene.text.{Font, FontWeight}
import scalafx.stage.Stage

package object tic_tac_toe {
  // Draws the game board and pieces given a game state
  def XODrawer(board: Array[Array[GamePiece]]): Unit = {
    val stage = new Stage()
    val grid = new GridPane()
    grid.padding = Insets(0, 100, 100, 20)

    grid.hgap = 2
    grid.vgap = 2

    for (x <- board.indices) {
      setColumnLabels(x + 1, grid)
      setRowLabels(x + 1, grid)
      for (y <- board.indices) {
        val stack = new StackPane()
        stack.setMinWidth(120)
        stack.setMinHeight(120)
        stack.setAlignment(Pos.Center)
        stack.setBackground(new Background(Array(new BackgroundFill(Color.Gray, null, null))))

        grid.add(stack, y + 1, x + 1)

        if (board(x)(y) != null)
          grid.add(getImage(board(x)(y).name, 120, 120), y + 1, x + 1)
      }
    }
    stage.title = "Tic-Tac-Toe"
    stage.scene = new Scene(475, 450) {
      fill = Burlywood
      content = grid
    }
    stage.show()
  }

  // Validates the user input according to the rules of the game
  // Applies the user action and modifies the board accordingly
  def XOController(state: GameState, move: List[String]): Array[Array[GamePiece]] = {
    if (move.head.length != 2)
      return state.board

    val x: Int = move.head(0) - '1'
    val y: Int = move.head(1) - 'a'

    if (!insideBoard(x, y, state.board) || state.board(x)(y) != null) {
      return state.board
    }

    // state.board = state.addMove(x, y, turn)
    val newBoard = addMove(x, y, state.board, state.player)

    //  true
    newBoard
  }

  // Apply the move on the board
  def addMove(row: Int, col: Int, board: Array[Array[GamePiece]], player: Boolean): Array[Array[GamePiece]] = {
    val newBoard = Array.tabulate(board.length, board.length)((x, y) =>
      if (x == row && y == col) GamePiece(if (player) "x" else "o", null)
      else board(x)(y))
    newBoard
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

  // Set rows labels
  def setRowLabels(x: Int, grid: GridPane): Unit = {
    val row = ('1' + x - 1).toChar.toString
    val label = new Label(row)
    label.setFont(Font.font("Arial", FontWeight.Bold, 25))
    val stack = new StackPane()
    stack.setMinWidth(45)
    stack.setMinHeight(45)
    stack.setAlignment(Pos.Center)
    stack.getChildren.add(label)
    grid.add(stack, 0, x)
  }

}
