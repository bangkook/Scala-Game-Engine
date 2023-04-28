package tic_tac_toe

import game_engine.Drawer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, FontWeight}

object XODrawer extends Drawer[XOBoard] {
  def draw(board: XOBoard): GridPane = {
    val grid = new GridPane()

    grid.padding = Insets(0, 100, 100, 20)

    grid.hgap = 2
    grid.vgap = 2

    // Set columns characters
    for (x <- 1 until board.size + 1) {
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

    // Set rows numbers
    for (x <- 1 until board.size + 1) {
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

    for (x <- 1 until board.size + 1)
      for (y <- 1 until board.size + 1) {
        val stack = new StackPane()
        stack.setMinWidth(120)
        stack.setMinHeight(120)
        stack.setAlignment(Pos.Center)
        stack.setBackground(new Background(Array(new BackgroundFill(Color.LightGray, null, null))))

        grid.add(stack, x, y)
      }

    for (x <- 0 until board.size)
      for (y <- 0 until board.size) {
        if (board.board(x)(y) != null)
          grid.add(board.board(x)(y).getImage, x + 1, y + 1)
      }

    grid
  }

}
