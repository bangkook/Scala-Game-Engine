package checkers

import game_engine.Drawer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.layout.{Background, BackgroundFill, GridPane, StackPane}
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, FontWeight}

object CheckersDrawer extends Drawer[CheckersBoard]{
  override def draw(board: CheckersBoard): GridPane = {
    val grid = new GridPane()
    grid.padding = Insets(0, 10, 10, 5)

    var white: Boolean = false
    val whiteBackground = new Background(Array(new BackgroundFill(Color.White, null, null)))
    val blackBackground = new Background(Array(new BackgroundFill(Color.Gray, null, null)))

    for (x <- 0 until board.size + 2) {
      val col = ('a' + x - 1).toChar.toString

      for (y <- 0 until board.size + 2) {
        val row = ('1' + y-1).toChar.toString

        val stack = new StackPane()
        stack.setMinWidth(45)
        stack.setMinHeight(45)
        stack.setAlignment(Pos.Center)

        val colLabel = new Label(col)
        colLabel.setFont(Font.font("Arial", FontWeight.Bold, 25))

        val rowLabel = new Label(row)
        rowLabel.setFont(Font.font("Arial", FontWeight.Bold, 25))

        // set column number
        if ((y == 0 && x > 0 && x < board.size +1) || (y == board.size + 1 && x > 0 && x < board.size + 1)) stack.getChildren.add(colLabel)
        // set row number
        else if ((x == 0 && y > 0 && y < board.size + 1)|| (x == board.size + 1 && y > 0 && y < board.size + 1)) stack.getChildren.add(rowLabel)
        // set tile color
        else if (x > 0 && y > 0 && y < board.size + 1) stack.setBackground(if (white) whiteBackground else blackBackground)
        grid.add(stack, x, y)

        // Switch tile color
        if (x+y % 2 != 0) white = !white
      }
      white = !white
    }
    // putting checker pieces on the board
    for (x <- 0 until board.size)
      for (y <- 0 until board.size) {
//        if(board.board(x)(y) == 1)
//          grid.add(board.board(x)(y))
        if (board.board(x)(y) != 0) {
          val piece = new CheckersPiece(board.board(x)(y))
          grid.add(piece.getImage, y + 1, x + 1)
//          grid.add()
        }
      }

    grid
  }

}
