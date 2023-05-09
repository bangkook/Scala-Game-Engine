package connect4

import game_engine.{GamePiece, getImage}
import javafx.scene.shape.Circle
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._
import scalafx.scene.text.{Font, FontWeight}
import scalafx.stage.Stage

object Connect4Drawer {
  def draw(board: Array[Array[GamePiece]]): Unit = {
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
        circle.setStroke(Color.BLACK)
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
