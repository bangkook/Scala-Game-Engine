package tic_tac_toe

import game_engine.Drawer
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.Label
import scalafx.scene.layout._
import scalafx.scene.paint.Color
import scalafx.scene.text.{Font, FontWeight}

import scala.annotation.tailrec

object XODrawer extends Drawer[XOBoard] {
  def draw(board: XOBoard): GridPane = {
    val grid = new GridPane()

    grid.padding = Insets(0, 100, 100, 20)

    grid.hgap = 2
    grid.vgap = 2

    // Set columns characters
    def setColumnLabels(x: Int): Unit = {
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

    def setRowLabels(x: Int): Unit = {
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

    // loop n times applying function fn
    @tailrec
    def loop(fn: Int => Unit, i: Int, n: Int): Unit = {
      if (i == n)
        return
      fn(i)
      loop(fn, i + 1, n)
    }

    loop(setColumnLabels, 1, board.size + 1)
    loop(setRowLabels, 1, board.size + 1)

    def add(): GridPane = {
      @tailrec
      def loop(x: Int, y: Int): GridPane = {
        if (x >= board.size)
          return grid
        if (y >= board.size)
          loop(x + 1, 0)
        else {
          val stack = new StackPane()
          stack.setMinWidth(120)
          stack.setMinHeight(120)
          stack.setAlignment(Pos.Center)
          stack.setBackground(new Background(Array(new BackgroundFill(Color.LightGray, null, null))))

          grid.add(stack, x + 1, y + 1)

          if (board.get(x, y) != null)
            grid.add(board.get(x, y).getImage, x + 1, y + 1)

          loop(x, y + 1)
        }
      }

      loop(0, 0)
    }

    add()
  }

}
