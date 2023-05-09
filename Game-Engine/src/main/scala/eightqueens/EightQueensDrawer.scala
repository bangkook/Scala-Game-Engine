package eightqueens

import game_engine.{GamePiece, getImage}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.Burlywood
import scalafx.scene.text.{Font, FontWeight}
import scalafx.stage.Stage

object EightQueensDrawer {
  def draw(board: Array[Array[GamePiece]]): Unit = {
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
