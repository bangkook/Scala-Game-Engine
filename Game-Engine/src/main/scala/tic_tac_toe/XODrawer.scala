package tic_tac_toe

import game_engine.Drawer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.layout._
import scalafx.scene.paint.Color

class XODrawer extends Drawer[XOBoard] {
  def draw(board: XOBoard): GridPane = {
    val grid = new GridPane()

    grid.padding = Insets(50, 100, 100, 50)

    grid.hgap = 2
    grid.vgap = 2
    for (x <- 0 until board.size)
      for (y <- 0 until board.size) {
        val stack = new StackPane()
        stack.setMinWidth(120)
        stack.setMinHeight(120)
        stack.setAlignment(Pos.Center)
        stack.setBackground(new Background(Array(new BackgroundFill(Color.DarkGoldenrod, null, null))))
        grid.add(stack, x, y)
      }
    var player = true

    for (x <- 0 until board.size)
      for (y <- 0 until board.size) {
        if (board.board(x)(y) != null)
          grid.add(board.board(x)(y).getImage, x, y)
      }

    grid
  }

}
