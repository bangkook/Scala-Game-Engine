import constants.Constants
import javafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, Button, Label, TextField}
import scalafx.scene.layout.{Background, BackgroundFill}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.LightGray
import scalafx.scene.text.Font
import scalafx.stage.Stage
import utility._

package object game_engine {
  case class GamePiece(
                        name: String,
                        color: String
                      )

  case class GameState(
                        player: Boolean,
                        board: Array[Array[GamePiece]]
                      )

  // Abstract game engine
  def gameEngine(controller: (GameState, List[String]) => GameState, drawer: Array[Array[GamePiece]] => Unit, game: String, gameState: GameState, stage: Stage): Unit = {
    val turn: Label = new Label(if (gameState.player) "Player 1's turn" else "Player 2's turn")
    turn.layoutX = 100
    turn.layoutY = 10
    turn.setFont(new Font(20))
    turn.setTextFill(Color.Orange)

    val inputHandler: (List[TextField], List[Label]) = game match {
      case Constants.connect => oneInputHandler
      case Constants.queens => oneInputHandler
      case Constants.tic_tac_toe => oneInputHandler
      case Constants.sudoku => sudokuInputHandler
      case Constants.chess => twoInputHandler
      case Constants.checkers => twoInputHandler
      case _ => null
    }

    val playButton: Button = new Button("Play")
    playButton.layoutX = 250
    playButton.layoutY = 80
    playButton.setFont(new Font(18))
    playButton.setMinSize(100, 50)
    playButton.background = new Background(Array(new BackgroundFill(Color.Cyan, null, null)))

    // Draw board
    drawer(gameState.board)

    // Stage to read input from user
    stage.title = "Input Window"
    stage.width = 400
    stage.height = 250
    stage.setX(10)
    stage.setY(100)
    stage.scene = new Scene() {
      fill = LightGray
      content = List(playButton) ++ cond(isDouble(game), turn) ++ inputHandler._1 ++ inputHandler._2
    }
    stage.show()

    // Game loop
    playButton.onAction = (_: ActionEvent) => {
      val input = inputHandler._1.map(x => x.getText)
      val alert = new Alert(AlertType.Error)
      val newGameState: GameState = controller(gameState, input)
      // If game's state changed, then the move was valid
      if (newGameState != gameState) {
        gameEngine(controller, drawer, game, newGameState, stage)
      } else {
        // Alert the user that the input was not valid
        alert.setContentText("Invalid Input")
        alert.show()
        //gameEngine(controller, drawer, game, gameState, stage)
      }
    }
  }
}
