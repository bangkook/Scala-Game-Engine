package checkers

import scalafx.scene.image.{Image, ImageView}

class CheckersPiece(pieceNum: Int) {
  val num: Int = pieceNum

  def getImage: ImageView = {
    var color: String = ""
    if(num == 1) color = "black"
    else if (num == -1) color = "white"
    val imageName = color + "_checker"
    val image = new Image(s"file:images/$imageName.png")
    val imageView = new ImageView(image)
    imageView.setFitWidth(45)
    imageView.setFitHeight(45)
    imageView
  }
}
