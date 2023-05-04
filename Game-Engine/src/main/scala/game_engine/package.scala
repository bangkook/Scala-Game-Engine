import scalafx.scene.image.{Image, ImageView}

package object game_engine {
  def getImage(name: String, width: Int, height: Int): ImageView = {
    val image1 = new Image(s"file:images/$name.png")
    val imageView1 = new ImageView(image1)
    imageView1.setFitWidth(width)
    imageView1.setFitHeight(height)
    imageView1
  }

  // Check move is within bounds
  def insideBoard(x: Int, y: Int, board: Array[Array[GamePiece]]): Boolean = {
    x >= 0 && x < board.length && y >= 0 && y < board.length
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

}
