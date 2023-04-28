package game_engine

import constants.Constants
import javafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, Label, TextField}
import scalafx.scene.layout.{Background, BackgroundFill, GridPane}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.LightGreen
import scalafx.scene.text.Font
import scalafx.stage.Stage

class GameEngine() {
  // Used to append to list when certain condition is met
  @inline def cond[T](p: => Boolean, v: T): List[T] = if (p) v :: Nil else Nil

  def play[U](controller: (U, List[String], Boolean) => Boolean, drawer: U => GridPane, game: String, board: U): Unit = {
    var player = true
    val stage = new Stage()
    stage.title = game
    stage.scene = new Scene(600, 450) {
      fill = LightGreen

      val turn: Label = new Label(if (player) "Player 1's turn" else "Player 2's turn")
      turn.layoutX = 460
      turn.layoutY = 10
      turn.setFont(new Font(20))
      turn.setTextFill(Color.Red)

      val inputHandler: (List[TextField], List[Label]) = game match {
        case Constants.connect => oneInputHandler
        case Constants.queens => oneInputHandler
        case Constants.tic_tac_toe => oneInputHandler
        case Constants.sudoku => sudokuInputHandler
        case Constants.chess => twoInputHandler
        case Constants.checkers => twoInputHandler
      }

      val playButton: Button = new Button("Play")
      playButton.layoutX = 475
      playButton.layoutY = 300
      playButton.setFont(new Font(18))
      playButton.setMinSize(100, 50)
      playButton.background = new Background(Array(new BackgroundFill(Color.Cyan, null, null)))
      playButton.onAction = (_: ActionEvent) => {
        val input = inputHandler._1.map(x => x.getText)
        val alert = new Alert(AlertType.None)

        if (controller(board, input, player)) {
          // Switch players in double games
          if (isDouble(game))
            player = !player
        } else {
          // Alert the user that the input was not valid
          alert.setAlertType(AlertType.Error)
          alert.setContentText("Invalid Input")
          alert.show()
        }

        turn.setText(if (player) "Player 1's turn" else "Player 2's turn")

        // Clear text fields
        inputHandler._1.foreach(x => x.clear())
        content = List(drawer(board), playButton) ++ cond(isDouble(game), turn) ++ inputHandler._1 ++ inputHandler._2
      }

      // Clear text fields
      inputHandler._1.foreach(x => x.clear())
      content = List(drawer(board), playButton) ++ cond(isDouble(game), turn) ++ inputHandler._1 ++ inputHandler._2
    }
    stage.show()
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
