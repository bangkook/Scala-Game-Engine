import game_engine.{GamePiece, GameState, insideBoard}
import scalafx.geometry.{Insets, Pos}
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.{Background, BackgroundFill, GridPane, StackPane}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.LightGreen
import scalafx.scene.text.{Font, FontWeight}
import scalafx.stage.Stage

package object sudoku {
  def sudokuDrawer(board: Array[Array[GamePiece]]): Unit = {
    val stage = new Stage()
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
        val num: String = if (board(x)(y).name == "0") " " else board(x)(y).name
        val l:Label=new Label("  "+ num)
        l.setFont(new Font(25))
        grid.add(l,y+1,x+1)
      }
    }

    stage.title = "Sudoku"
    stage.scene = new Scene(475, 450) {
      fill = LightGreen
      content = grid
    }
    stage.show()

  }

  def sudokuController(state: GameState, move: List[String]): GameState = {
    if (move.head.length != 2 || move(1).length != 1)
      return state

    val x: Int = move.head(0) - '1'
    val y: Int = move.head(1) - 'a'
    val z: Int = move(1)(0)-'0'
    val delete:Boolean= (z==0)


    //out of bound
    if (!insideBoard(x, y, state.board) ) {
      println("out of bound")
      return state
    }

    if(!delete ) {
      //breaks the rules
      if (!validAdd(state.board, x, y, z)) {
        println("invalid move")
        return state
      }else{
        val newBoard = addMove(x, y, z, state.board)
        return GameState(state.player, newBoard)
      }

    }else{// delete
      if(!validDelete(x,y,state.board)){
        println("can't delete this")
        return state
      }else{
        val newBoard = removeMove(x, y, state.board)
        return GameState(state.player, newBoard)
      }
    }
  }

  def validAdd(board: Array[Array[GamePiece]], x:Int, y:Int, z:Int): Boolean = {
    if (!(z >= 1 && z <= 9)) {
      println("z out of bound")
      return false
    }
    //has a number
    if (board(x)(y).color == "init") {
      println("has an init number")
      return false
    }

    for(i<-0 until 9){
      if(board(x)(i).name==z.toString){
        println("same val in same col")
        return false
      }
      if(board(i)(y).name==z.toString) {
        println("same val in same row")
        return false
      }

    }
    val startrow=x-(x%3)
    val startcol=y-(y%3)
    for (i <- 0 until 3) {
      for (j <- 0 until 3) {
        if (board(i+startrow)(j+startcol).name == z.toString) {
          println("same val in same box")
          return false
        }
      }
    }
    true
  }

  def validDelete(x: Int, y: Int,board: Array[Array[GamePiece]]): Boolean = {
    board(x)(y).color != "init"
  }

  def addMove(x1: Int, y1: Int, z: Int,board: Array[Array[GamePiece]]): Array[Array[GamePiece]] = {
    val newBoard: Array[Array[GamePiece]] = Array.tabulate(board.length, board.length)((x, y) =>
      if (x==x1 && y==y1) GamePiece(z.toString, "free")
      else board(x)(y)
    )
    newBoard
  }

  def removeMove(x1: Int, y1: Int,board: Array[Array[GamePiece]]): Array[Array[GamePiece]] = {
    val newBoard: Array[Array[GamePiece]] = Array.tabulate(board.length, board.length)((x, y) =>
      if (x == x1 && y == y1) GamePiece("0", "free")
      else board(x)(y)
    )
    newBoard
  }
}
