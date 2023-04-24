package chess

import scalafx.scene.image.{Image, ImageView}

class ChessPiece(pieceColor: String, pieceName: String) {
  val color: String = pieceColor
  val name: String = pieceName

  def getImage: ImageView = {
    val imageName = color + "-" + name
    val image = new Image(s"file:images/$imageName.png")
    val imageView = new ImageView(image)
    imageView.setFitWidth(45)
    imageView.setFitHeight(45)
    imageView
  }
}
