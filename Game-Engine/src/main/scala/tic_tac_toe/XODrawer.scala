package tic_tac_toe

import game_engine.GamePiece
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.LightGreen
import scalafx.scene.text.{Font, FontWeight}
import scalafx.stage.Stage

object XODrawer {
  def draw(board: Array[Array[GamePiece]]): Unit = {
    val stage = new Stage()
    val grid = new GridPane()
    grid.padding = Insets(0, 100, 100, 20)

    grid.hgap = 2
    grid.vgap = 2

    for (x <- 1 until board.length + 1) {
      setColumnLabels(x, grid)
      setRowLabels(x, grid)
    }

    for (x <- board.indices) {
      for (y <- board.indices) {
        val stack = new StackPane()
        stack.setMinWidth(120)
        stack.setMinHeight(120)
        stack.setAlignment(Pos.Center)
        stack.setBackground(new Background(Array(new BackgroundFill(Color.LightGray, null, null))))

        grid.add(stack, y + 1, x + 1)

        if (board(x)(y) != null)
          grid.add(XOPiece.getImage(board(x)(y).name), y + 1, x + 1)
      }
    }
    stage.title = "Tic-Tac-Toe"
    stage.scene = new Scene(500, 500) {
      fill = LightGreen
      content = grid
    }
    stage.show()
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
