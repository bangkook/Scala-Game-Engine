import constants.Constants
import game_engine.GamePiece
import scalafx.scene.control.{Label, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.text.Font
import sudoku.SudokuGenerator

// Utility functions invoked by other functions
package object utility {
  // Used to append to list when certain condition is met
  @inline def cond[T](p: => Boolean, v: T): List[T] = if (p) v :: Nil else Nil

  // check whether the game is 2 player game or not
  def isDouble(game: String): Boolean = {
    game match {
      case Constants.sudoku => false
      case Constants.queens => false
      case _ => true
    }
  }

  def oneInputHandler: (List[TextField], List[Label]) = {
    val label = new Label("Cell")
    label.layoutX = 10
    label.layoutY = 100
    label.setFont(new Font(16))
    val textFieldX: TextField = new TextField
    textFieldX.layoutX = 40
    textFieldX.layoutY = 100
    textFieldX.setMaxSize(50, 50)
    textFieldX.setPromptText("e.g. 1a")

    (List(textFieldX), List(label))
    /* (isDouble(game))
      println(if (player) "Player 1's turn" else "Player 2's turn")
    val cell = readLine("Cell (e.g. 1a): ")
    List(cell)*/
  }

  def sudokuInputHandler: (List[TextField], List[Label]) = {
    val labelPos = new Label("Cell")
    labelPos.layoutX = 10
    labelPos.layoutY = 75
    labelPos.setFont(new Font(16))

    val textFieldPos: TextField = new TextField
    textFieldPos.layoutX = 40
    textFieldPos.layoutY = 75
    textFieldPos.setMaxSize(50, 50)
    textFieldPos.setPromptText("e.g. 1a")

    val labelVal = new Label("Value")
    labelVal.layoutX = 0
    labelVal.layoutY = 150
    labelVal.setFont(new Font(16))

    val textFieldVal: TextField = new TextField
    textFieldVal.layoutX = 40
    textFieldVal.layoutY = 150
    textFieldVal.setMaxSize(50, 50)
    textFieldVal.setPromptText("1:9 or 0 to delete")

    (List(textFieldPos, textFieldVal), List(labelPos, labelVal))
  }

  def twoInputHandler: (List[TextField], List[Label]) = {
    val labelFrom: Label = new Label("From")
    labelFrom.layoutX = 0
    labelFrom.layoutY = 75
    labelFrom.setFont(new Font(16))

    val textFieldFrom: TextField = new TextField
    textFieldFrom.layoutX = 40
    textFieldFrom.layoutY = 75
    textFieldFrom.setMaxSize(50, 50)
    textFieldFrom.setPromptText("e.g. 1a")

    val labelTo: Label = new Label("To")
    labelTo.layoutX = 10
    labelTo.layoutY = 150
    labelTo.setFont(new Font(16))

    val textFieldTo: TextField = new TextField
    textFieldTo.layoutX = 40
    textFieldTo.layoutY = 150
    textFieldTo.setMaxSize(50, 50)
    textFieldTo.setPromptText("e.g. 2a")

    (List(textFieldFrom, textFieldTo), List(labelFrom, labelTo))
  }

  def getImage(name: String, width: Int, height: Int): ImageView = {
    val image1 = new Image(s"file:images/$name.png")
    val imageView1 = new ImageView(image1)
    imageView1.setFitWidth(width)
    imageView1.setFitHeight(height)
    imageView1
  }

  // Check move is within bounds
  def insideBoard(x: Int, y: Int, board: Array[Array[GamePiece]]): Boolean = {
    x >= 0 && x < board.length && y >= 0 && y < board(0).length
  }

  // Initialize chess board
  def initializeChess: Array[Array[GamePiece]] = {
    val black: String = "black"
    val white: String = "white"
    val pawn: String = "pawn"
    val chessPieces: Array[String] = Array("rook", "knight", "bishop", "queen", "king", "bishop", "knight", "rook")

    val size = 8
    val board = Array.tabulate(size, size)((x, y) => {
      if (x == 0) GamePiece(chessPieces(y), black)
      else if (x == 1) GamePiece( pawn,black)
      else if (x == size - 1) GamePiece(chessPieces(y), white)
      else if (x == size - 2) GamePiece(pawn, white)
      else null
    })
    board
  }

  def initializeCheckers: Array[Array[GamePiece]] = {
    val black: String = "black"
    val white: String = "white"
    val size = 8
    val board = Array.tabulate(size, size)((x, y) => {
      if ((x + y) % 2 != 0 && x < (size / 2) - 1) GamePiece("checker", black)
      else if ((x + y) % 2 != 0 && x > (size / 2)) GamePiece("checker", white)
      else null
    })
    board
  }

  def initializeSudoku: Array[Array[GamePiece]] = {
    val size = 9
    val initialBoard: Array[Array[Int]] = Array.ofDim[Int](size, size)
    SudokuGenerator.getSudoku(initialBoard)
    val board = Array.tabulate(size, size)((x, y) => {
      if (initialBoard(x)(y) != 0) GamePiece(initialBoard(x)(y).toString, "init")
      else GamePiece("0", "free")
    })
    board
  }

  def initializeEightQueens: Array[Array[GamePiece]] = {
    val size = 8
    val board = Array.tabulate(size + 1, size)((x, _) => {
      if (x == 8) GamePiece("queens", null)
      else null
    })
    board
  }

}
