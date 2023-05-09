import checkers.{checkersController, checkersDrawer}
import chess.{chessController, chessDrawer}
import connect4.{connect4Controller, connect4Drawer}
import constants.Constants
import utility.{initializeCheckers, initializeChess, initializeEightQueens, initializeSudoku}
import game_engine.{GamePiece, GameState, gameEngine}
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
import sudoku.{sudokuController, sudokuDrawer}
import tic_tac_toe.{XOController, XODrawer}
import eight_queens.{queensController, queensDrawer}

import javax.swing.ImageIcon

object InitialScreen extends JFXApp3 {
  override def start(): Unit = {
    stage = new JFXApp3.PrimaryStage {
      title.value = "Game Engine"
      width = 650
      height = 450
      scene = new Scene {
        fill = LightGreen

        val label = new Label("Game Engine")
        label.setFont(new Font(30))
        label.layoutX = 200
        label.layoutY = 10
        label.setTextFill(Color.Brown)

        // Tic-Tac-Toe
//        val image1 = new Image("file:images/tic-tac-toe.png")
        val image1 = new ImageIcon(getClass().getClassLoader().getResource("images/tic-tac-toe.png")).toString()
        val imageView1 = new ImageView(image1)
        imageView1.setFitWidth(100)
        imageView1.setFitHeight(100)

        val tic_tac_toe: Button = new Button()
        tic_tac_toe.setFont(new Font(20))
        tic_tac_toe.layoutX = 50
        tic_tac_toe.layoutY = 75
        tic_tac_toe.setGraphic(imageView1)

        tic_tac_toe.onAction = (_: ActionEvent) => {
          val gameState: GameState = GameState(player = true, Array.ofDim[GamePiece](3, 3))
          gameEngine(XOController, XODrawer, Constants.tic_tac_toe, gameState, new Stage())
        }

        // Chess
//        val image2 = new Image("file:images/chess.png")
        val image2 = new ImageIcon(getClass().getClassLoader().getResource("images/chess.png")).toString()
        val imageView2 = new ImageView(image2)
        imageView2.setFitWidth(100)
        imageView2.setFitHeight(100)

        val chess: Button = new Button()
        chess.setFont(new Font(20))
        chess.layoutX = 250
        chess.layoutY = 75
        chess.setGraphic(imageView2)

        chess.onAction = (_: ActionEvent) => {
          val gameState: GameState = GameState(player = true, initializeChess)
          gameEngine(chessController, chessDrawer, Constants.chess, gameState, new Stage())
        }

        // Connect 4
//        val image3 = new Image("file:images/connect.png")
        val image3 = new ImageIcon(getClass().getClassLoader().getResource("images/connect.png")).toString()
        val imageView3 = new ImageView(image3)
        imageView3.setFitWidth(100)
        imageView3.setFitHeight(100)

        val connect4: Button = new Button()
        connect4.setFont(new Font(20))
        connect4.layoutX = 450
        connect4.layoutY = 75
        connect4.setGraphic(imageView3)

        connect4.onAction = (_: ActionEvent) => {
          val gameState: GameState = GameState(player = true, Array.ofDim[GamePiece](6, 7))
          gameEngine(connect4Controller, connect4Drawer, Constants.connect, gameState, new Stage())
        }

        // Checkers
//        val image4 = new Image("file:images/checker.png")
        val image4 = new ImageIcon(getClass().getClassLoader().getResource("images/checker.png")).toString()
        val imageView4 = new ImageView(image4)
        imageView4.setFitWidth(100)
        imageView4.setFitHeight(100)

        val checkers: Button = new Button()
        checkers.setFont(new Font(20))
        checkers.layoutX = 50
        checkers.layoutY = 225
        checkers.setGraphic(imageView4)

        checkers.onAction = (_: ActionEvent) => {
          val gameState: GameState = GameState(player = true, initializeCheckers)
          gameEngine(checkersController, checkersDrawer, Constants.checkers, gameState, new Stage())
        }

        // Sudoku
//        val image5 = new Image("file:images/sudoku.png")
        val image5 = new ImageIcon(getClass().getClassLoader().getResource("images/sudoku.png")).toString()
        val imageView5 = new ImageView(image5)
        imageView5.setFitWidth(100)
        imageView5.setFitHeight(100)

        val sudoku: Button = new Button()
        sudoku.setFont(new Font(20))
        sudoku.layoutX = 250
        sudoku.layoutY = 225
        sudoku.setGraphic(imageView5)

        sudoku.onAction = (_: ActionEvent) => {
          val gameState: GameState = GameState(player = true, initializeSudoku)
          gameEngine(sudokuController, sudokuDrawer, Constants.sudoku, gameState, new Stage())
        }

        // 8-Queens
//        val image6 = new Image("file:images/queens.png")
        val image6 = new ImageIcon(getClass().getClassLoader().getResource("images/queens.png")).toString()
        val imageView6 = new ImageView(image6)
        imageView6.setFitWidth(100)
        imageView6.setFitHeight(100)

        val queens: Button = new Button()
        queens.setFont(new Font(20))
        queens.layoutX = 450
        queens.layoutY = 225
        queens.setGraphic(imageView6)

        queens.onAction = (_: ActionEvent) => {
          val gameState: GameState = GameState(player = false, initializeEightQueens)
          gameEngine(queensController, queensDrawer, Constants.queens, gameState, new Stage())
        }
        content = List(tic_tac_toe, chess, connect4, checkers, sudoku, queens)
      }
    }
  }
}
