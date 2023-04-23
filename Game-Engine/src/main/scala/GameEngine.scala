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
// TODO : Add label to each text field and adjust their positions

class GameEngine() {
  // Each game has name and number of input fields
  val tic_tac_toe: (String, Int) = ("Tic-Tac-Toe", 2)
  val checkers: (String, Int) = ("Checkers", 4)
  val connect: (String, Int) = ("Connect-4", 1)
  val queens: (String, Int) = ("8-Queens", 2)
  val sudoku: (String, Int) = ("Sudoku", 3)
  val chess: (String, Int) = ("Chess", 4)

  def play(controller: (XOBoard, List[Int], Boolean) => Boolean, drawer: XOBoard => GridPane, game: String): Unit = {
    var board = new XOBoard
    var player = true
    val stage = new Stage()
    stage.title = game
    stage.scene = new Scene(600, 450) {
      fill = LightGreen

      val turn: Label = new Label(if (player) "Player1's turn" else "Player2's turn")
      turn.layoutX = 450
      turn.layoutY = 50
      turn.setFont(new Font(20))
      turn.setTextFill(Color.Red)

      var inputFields: List[TextField] = List()

      game match {
        case connect._1 => inputFields = oneInputHandler()
        case queens._1 => inputFields = twoInputHandler()
        case tic_tac_toe._1 => inputFields = twoInputHandler()
        case sudoku._1 => inputFields = threeInputHandler()
        case chess._1 => inputFields = fourInputHandler()
        case checkers._1 => inputFields = fourInputHandler()
      }

      val playButton: Button = new Button("Play")
      playButton.layoutX = 510
      playButton.layoutY = 300
      playButton.background = new Background(Array(new BackgroundFill(Color.Cyan, null, null)))
      playButton.onAction = (event: ActionEvent) => {
        var input: List[Int] = List()
        for (textField <- inputFields)
          input = textField.getText.toInt :: input

        if (controller(board, input, player))
          player = !player
        turn.setText(if (player) "Player1's turn" else "Player2's turn")
        content = List(drawer(board), turn, playButton)
        for (textField <- inputFields)
          content.add(textField)
      }

      content = List(drawer(board), turn, playButton)
      for (textField <- inputFields)
        content.add(textField)
      //controller()

    }
    stage.show()

  }

  def oneInputHandler(): List[TextField] = {
    val textFieldX: TextField = new TextField
    textFieldX.layoutX = 500
    textFieldX.layoutY = 100
    textFieldX.setMaxSize(50, 50)

    List(textFieldX)
  }

  def twoInputHandler(): List[TextField] = {
    val textFieldX: TextField = new TextField
    textFieldX.layoutX = 500
    textFieldX.layoutY = 100
    textFieldX.setMaxSize(50, 50)

    val textFieldY: TextField = new TextField
    textFieldY.layoutX = 500
    textFieldY.layoutY = 200
    textFieldY.setMaxSize(50, 50)

    List(textFieldX, textFieldY)
  }

  def threeInputHandler(): List[TextField] = {
    val textFieldX: TextField = new TextField
    textFieldX.layoutX = 500
    textFieldX.layoutY = 100
    textFieldX.setMaxSize(50, 50)

    val textFieldY: TextField = new TextField
    textFieldY.layoutX = 500
    textFieldY.layoutY = 200
    textFieldY.setMaxSize(50, 50)

    val textFieldVal: TextField = new TextField
    textFieldVal.layoutX = 500
    textFieldVal.layoutY = 200
    textFieldVal.setMaxSize(50, 50)

    List(textFieldX, textFieldY, textFieldVal)
  }

  def fourInputHandler(): List[TextField] = {
    val textFieldFromX: TextField = new TextField
    textFieldFromX.layoutX = 500
    textFieldFromX.layoutY = 100
    textFieldFromX.setMaxSize(50, 50)

    val textFieldFromY: TextField = new TextField
    textFieldFromY.layoutX = 500
    textFieldFromY.layoutY = 200
    textFieldFromY.setMaxSize(50, 50)

    val textFieldToX: TextField = new TextField
    textFieldToX.layoutX = 500
    textFieldToX.layoutY = 200
    textFieldToX.setMaxSize(50, 50)

    val textFieldToY: TextField = new TextField
    textFieldToY.layoutX = 500
    textFieldToY.layoutY = 200
    textFieldToY.setMaxSize(50, 50)

    List(textFieldFromX, textFieldFromY, textFieldToX, textFieldToY)
  }

}
