package game_engine

import constants.Constants
import javafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, Label, TextField}
import scalafx.scene.layout.{Background, BackgroundFill}
import scalafx.scene.paint.Color
import scalafx.scene.text.Font
import scalafx.stage.Stage

import scala.io.StdIn.readLine

case class GamePiece(name: String, color: String)

case class GameState(
                      player: Boolean,
                      board: Array[Array[GamePiece]]
                    )

class GameEngine {
  // Used to append to list when certain condition is met
  @inline def cond[T](p: => Boolean, v: T): List[T] = if (p) v :: Nil else Nil

  def play(controller: (GameState, List[String]) => GameState, drawer: Array[Array[GamePiece]] => Unit, game: String, gameState: GameState): Unit = {
    val turn: Label = new Label(if (gameState.player) "Player 1's turn" else "Player 2's turn")
    turn.layoutX = 0
    turn.layoutY = 0
    turn.setFont(new Font(20))
    turn.setTextFill(Color.Orange)

    val playButton: Button = new Button("Play")
    playButton.layoutX = 0
    playButton.layoutY = 80
    playButton.setFont(new Font(18))
    playButton.setMinSize(100, 50)
    playButton.background = new Background(Array(new BackgroundFill(Color.Cyan, null, null)))

    val inputHandler: (List[TextField], List[Label]) = game match {
      case Constants.connect => oneInputHandler
      case Constants.queens => oneInputHandler
      case Constants.tic_tac_toe => oneInputHandler
      case Constants.sudoku => sudokuInputHandler
      case Constants.chess => twoInputHandler
      case Constants.checkers => twoInputHandler
    }

    // Initial game stage
    drawer(gameState.board)

    // Stage for taking input
    val stage = new Stage()
    stage.title = "Input Screen"
    stage.scene = new Scene(100, 100) {
      content = List(playButton) ++ cond(isDouble(game), turn) ++ inputHandler._1 ++ inputHandler._2
    }
    stage.show()

    // Game loop
    playButton.onAction = (_: ActionEvent) => {
      //val input = inputHandler._1.map(x => x.getText)
      val i = readLine()
      val input = List(i)
      val alert = new Alert(AlertType.Error)
      val newGameState: GameState = controller(gameState, input)
      // If game's state changed, then the move was valid
      if (newGameState != gameState) {
        drawer(newGameState.board)
        // turn.setText(if (newGameState.player) "Player 1's turn" else "Player 2's turn")
      } else {
        // Alert the user that the input was not valid
        alert.setContentText("Invalid Input")
        alert.show()
      }
    }
  }


  def isDouble(game: String): Boolean = {
    game match {
      case Constants.sudoku => false
      case Constants.queens => false
      case _ => true
    }
  }

  def oneInputHandler: (List[TextField], List[Label]) = {
    val label = new Label("Cell")
    label.layoutX = 470
    label.layoutY = 100
    label.setFont(new Font(16))
    val textFieldX: TextField = new TextField
    textFieldX.layoutX = 500
    textFieldX.layoutY = 100
    textFieldX.setMaxSize(50, 50)
    textFieldX.setPromptText("e.g. 1a")

    (List(textFieldX), List(label))
    /* if (isDouble(game))
       println(if (player) "Player 1's turn" else "Player 2's turn")
     print("Cell (e.g. 1a): ")
     val cell = readLine()
     List(cell)*/
  }

  def sudokuInputHandler: (List[TextField], List[Label]) = {
    val labelPos = new Label("Cell")
    labelPos.layoutX = 470
    labelPos.layoutY = 100
    labelPos.setFont(new Font(16))

    val textFieldPos: TextField = new TextField
    textFieldPos.layoutX = 500
    textFieldPos.layoutY = 100
    textFieldPos.setMaxSize(50, 50)
    textFieldPos.setPromptText("e.g. 1a")

    val labelVal = new Label("Value")
    labelVal.layoutX = 460
    labelVal.layoutY = 200
    labelVal.setFont(new Font(16))

    val textFieldVal: TextField = new TextField
    textFieldVal.layoutX = 500
    textFieldVal.layoutY = 200
    textFieldVal.setMaxSize(50, 50)
    textFieldVal.setPromptText("1:9")

    (List(textFieldPos, textFieldVal), List(labelPos, labelVal))
  }

  def twoInputHandler: (List[TextField], List[Label]) = {
    val labelFrom: Label = new Label("From")
    labelFrom.layoutX = 460
    labelFrom.layoutY = 100
    labelFrom.setFont(new Font(16))

    val textFieldFrom: TextField = new TextField
    textFieldFrom.layoutX = 500
    textFieldFrom.layoutY = 100
    textFieldFrom.setMaxSize(50, 50)
    textFieldFrom.setPromptText("e.g. 1a")

    val labelTo: Label = new Label("To")
    labelTo.layoutX = 470
    labelTo.layoutY = 200
    labelTo.setFont(new Font(16))

    val textFieldTo: TextField = new TextField
    textFieldTo.layoutX = 500
    textFieldTo.layoutY = 200
    textFieldTo.setMaxSize(50, 50)
    textFieldTo.setPromptText("e.g. 2a")

    (List(textFieldFrom, textFieldTo), List(labelFrom, labelTo))
  }
}
