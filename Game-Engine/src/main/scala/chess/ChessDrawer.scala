package chess

import game_engine.Drawer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, FontWeight}

object ChessDrawer extends Drawer[ChessBoard] {
  override def draw(board: ChessBoard): GridPane = {
    val grid = new GridPane()
    grid.padding = Insets(0, 10, 10, 5)

    var white: Boolean = false
    val whiteBackground = new Background(Array(new BackgroundFill(Color.White, null, null)))
    val blackBackground = new Background(Array(new BackgroundFill(Color.Brown, null, null)))

    for (x <- 0 until board.size + 1) {
      val col = ('a' + x - 1).toChar.toString

      for (y <- 0 until board.size + 1) {
        val row = ('1' + board.size - y).toChar.toString

        val stack = new StackPane()
        stack.setMinWidth(45)
        stack.setMinHeight(45)
        stack.setAlignment(Pos.Center)

        val colLabel = new Label(col)
        colLabel.setFont(Font.font("Arial", FontWeight.Bold, 25))

        val rowLabel = new Label(row)
        rowLabel.setFont(Font.font("Arial", FontWeight.Bold, 25))

        // set column number
        if (y == 0 && x > 0) stack.getChildren.add(colLabel)
        // set row number
        else if (x == 0 && y > 0) stack.getChildren.add(rowLabel)
        // set tile color
        else if (x > 0 && y > 0) stack.setBackground(if (white) whiteBackground else blackBackground)
        grid.add(stack, x, y)

        // Switch tile color
        if (x > 0 && y > 0) white = !white
      }
      white = !white
    }

    for (x <- 0 until board.size)
      for (y <- 0 until board.size) {
        if (board.board(x)(y) != null)
          grid.add(board.board(x)(y).getImage, x + 1, y + 1)
      }

    grid
  }

}
