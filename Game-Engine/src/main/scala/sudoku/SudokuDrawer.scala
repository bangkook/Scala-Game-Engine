package sudoku

import game_engine.Drawer
import scalafx.event.ActionEvent
import scalafx.scene.text.{Font, FontWeight}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.layout.{Background, BackgroundFill, GridPane, StackPane}
import scalafx.scene.paint.Color

object SudokuDrawer extends Drawer[SudokuBoard]{
  override def draw(board: SudokuBoard): GridPane = {
    val grid = new GridPane()
    grid.padding = Insets(5,5,5,5)

    grid.hgap = 2
    grid.vgap = 2

    for (x <- 0 until board.size+1) {
      val col = ('a' + x - 1).toChar.toString
      for (y <- 0 until board.size+1) {
        val row = ('1' +y-1).toChar.toString

        val stack = new StackPane()
        stack.setMinWidth(40)
        stack.setMinHeight(40)
        stack.setAlignment(Pos.Center)

        val colLabel = new Label(col)
        colLabel.setFont(Font.font("Arial", FontWeight.Bold, 20))

        val rowLabel = new Label(row)
        rowLabel.setFont(Font.font("Arial", FontWeight.Bold, 20))

        if (y == 0 && x > 0) stack.getChildren.add(colLabel)
        // set row number
        else if (x == 0 && y > 0) stack.getChildren.add(rowLabel)

        else if (x > 0 && y > 0)
        if(4<=y && y<7) stack.setBackground(new Background(Array(new BackgroundFill(Color.LightPink, null, null))))
        else if(4<=x && x<7)  stack.setBackground(new Background(Array(new BackgroundFill(Color.LightYellow, null, null))))
        else stack.setBackground(new Background(Array(new BackgroundFill(Color.LightCyan, null, null))))

        //if(3<x &&x<6)  stack.setBackground(new Background(Array(new BackgroundFill(Color.Cyan, null, null))))
        grid.add(stack, x, y)
      }
    }

    for (x <- 0 until board.size) {
      for (y <- 0 until board.size) {
        var num:String=if(board.board(x)(y)==0) " " else board.board(x)(y).toString
        val l:Label=new Label("  "+ num)
        l.setFont(new Font(25))
        grid.add(l,y+1,x+1)
        print(board.board(x)(y))
      }
      println(" ")
    }
    grid
  }
}
