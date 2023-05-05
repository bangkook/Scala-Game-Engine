import checkers.{CheckersController, CheckersDrawer}
import chess.{ChessController, ChessDrawer}
import constants.Constants
import game_engine.{GameEngine, GamePiece, GameState, initializeCheckers, initializeChess}
import scalafx.Includes._
import scalafx.application.JFXApp3
import scalafx.event.ActionEvent
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._
import scalafx.scene.text.Font
import scalafx.stage.Stage
import tic_tac_toe.{XOController, XODrawer}

object InitialScreen extends JFXApp3 {
  override def start(): Unit = {
    val gameEngine = new GameEngine

    stage = new JFXApp3.PrimaryStage {
      title.value = "Game Engine"
      width = 650
      height = 450
      scene = new Scene {
        fill = LightGreen

        val label = new Label("Game Engineeto")
        label.setFont(new Font(30))
        label.layoutX = 200
        label.layoutY = 10
        label.setTextFill(Color.Brown)

        // Tic-Tac-Toe
        val image1 = new Image("file:images/tic-tac-toe.png")
        val imageView1 = new ImageView(image1)
        imageView1.setFitWidth(100)
        imageView1.setFitHeight(100)

        val tic_tac_toe: Button = new Button()
        tic_tac_toe.setFont(new Font(20))
        tic_tac_toe.layoutX = 50
        tic_tac_toe.layoutY = 75
        tic_tac_toe.setGraphic(imageView1)

        tic_tac_toe.onAction = (event: ActionEvent) => {
          val gameState: GameState = GameState(player = true, Array.ofDim[GamePiece](3, 3))
          gameEngine.play(XOController.control, XODrawer.draw, Constants.tic_tac_toe, gameState, new Stage())
        }

        // Chess
        val image2 = new Image("file:images/chess.png")
        val imageView2 = new ImageView(image2)
        imageView2.setFitWidth(100)
        imageView2.setFitHeight(100)

        val chess: Button = new Button()
        chess.setFont(new Font(20))
        chess.layoutX = 250
        chess.layoutY = 75
        chess.setGraphic(imageView2)

        chess.onAction = (event: ActionEvent) => {
          val gameState: GameState = GameState(player = true, initializeChess)
          //  val gameState: GameState[ChessBoard] = GameState(player = true, chessBoard)
          gameEngine.play(ChessController.control, ChessDrawer.draw, Constants.chess, gameState, new Stage())
        }

        // Connect 4
        val image3 = new Image("file:images/connect.png")
        val imageView3 = new ImageView(image3)
        imageView3.setFitWidth(100)
        imageView3.setFitHeight(100)

        val connect4: Button = new Button()
        connect4.setFont(new Font(20))
        connect4.layoutX = 450
        connect4.layoutY = 75
        connect4.setGraphic(imageView3)

        connect4.onAction = (event: ActionEvent) => {
          println("Connect")
        }

        // Checkers
        val image4 = new Image("file:images/checker.png")
        val imageView4 = new ImageView(image4)
        imageView4.setFitWidth(100)
        imageView4.setFitHeight(100)

        val checkers: Button = new Button()
        checkers.setFont(new Font(20))
        checkers.layoutX = 50
        checkers.layoutY = 225
        checkers.setGraphic(imageView4)

        checkers.onAction = (event: ActionEvent) => {
          println("CHECKERS")
          val gameState: GameState = GameState(player = true, initializeCheckers)
          gameEngine.play(CheckersController.control, CheckersDrawer.draw, Constants.checkers, gameState, new Stage())
        }

        // Sudoku
        val image5 = new Image("file:images/sudoku.png")
        val imageView5 = new ImageView(image5)
        imageView5.setFitWidth(100)
        imageView5.setFitHeight(100)

        val sudoku: Button = new Button()
        sudoku.setFont(new Font(20))
        sudoku.layoutX = 250
        sudoku.layoutY = 225
        sudoku.setGraphic(imageView5)

        sudoku.onAction = (event: ActionEvent) => {
          //  gameEngine.play[SudokuBoard](SudokuController.control, SudokuDrawer.draw, Constants.sudoku, new SudokuBoard)
        }

        // 8-Queens
        val image6 = new Image("file:images/queens.png")
        val imageView6 = new ImageView(image6)
        imageView6.setFitWidth(100)
        imageView6.setFitHeight(100)

        val queens: Button = new Button()
        queens.setFont(new Font(20))
        queens.layoutX = 450
        queens.layoutY = 225
        queens.setGraphic(imageView6)

        queens.onAction = (event: ActionEvent) => {
          println("TTT")
        }
        content = List(label, tic_tac_toe, chess, connect4, checkers, sudoku, queens)
      }
    }
  }
}
