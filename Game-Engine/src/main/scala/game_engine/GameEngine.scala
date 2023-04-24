package game_engine

import javafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.layout.{Background, BackgroundFill, GridPane}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.LightGreen
import scalafx.scene.text.Font
import scalafx.stage.Stage

// TODO : check single or double
// TODO: handle null input
// TODO : think of another way to handle game details

class GameEngine() {
  // Each game has name and number of input fields
  val tic_tac_toe: (String, Int) = ("Tic-Tac-Toe", 2)
  val checkers: (String, Int) = ("Checkers", 4)
  val connect: (String, Int) = ("Connect-4", 1)
  val queens: (String, Int) = ("8-Queens", 2)
  val sudoku: (String, Int) = ("Sudoku", 3)
  val chess: (String, Int) = ("Chess", 4)

  def play[U](controller: (U, List[String], Boolean) => Boolean, drawer: U => GridPane, game: String, board: U): Unit = {
    var player = true
    val stage = new Stage()
    stage.title = game
    stage.scene = new Scene(600, 450) {
      fill = LightGreen

      val turn: Label = new Label(if (player) "Player1's turn" else "Player2's turn")
      turn.layoutX = 460
      turn.layoutY = 10
      turn.setFont(new Font(20))
      turn.setTextFill(Color.Red)

      var inputFields: (List[TextField], List[Label]) = (List(), List())
      game match {
        case connect._1 => inputFields = oneInputHandler()
        case queens._1 => inputFields = oneInputHandler()
        case tic_tac_toe._1 => inputFields = oneInputHandler()
        case sudoku._1 => inputFields = sudokuInputHandler()
        case chess._1 => inputFields = twoInputHandler()
        case checkers._1 => inputFields = twoInputHandler()
      }

      val playButton: Button = new Button("Play")
      playButton.layoutX = 475
      playButton.layoutY = 300
      playButton.setFont(new Font(18))
      playButton.setMinSize(100, 50)
      playButton.background = new Background(Array(new BackgroundFill(Color.Cyan, null, null)))
      playButton.onAction = (event: ActionEvent) => {
        var input: List[String] = List()
        for (textField <- inputFields._1)
          input = textField.getText :: input

        if (controller(board, input, player))
          player = !player
        turn.setText(if (player) "Player1's turn" else "Player2's turn")
        content = List(drawer(board), turn, playButton)
        for (textField <- inputFields._1) {
          textField.clear()
          content.add(textField)
        }
        for (label <- inputFields._2)
          content.add(label)
      }

      content = List(drawer(board), turn, playButton)
      for (textField <- inputFields._1) {
        textField.clear()
        content.add(textField)
      }
      for (textField <- inputFields._2)
        content.add(textField)
    }
    stage.show()
  }

  def oneInputHandler(): (List[TextField], List[Label]) = {
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

  def sudokuInputHandler(): (List[TextField], List[Label]) = {
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

  def twoInputHandler(): (List[TextField], List[Label]) = {
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
