package tic_tac_toe

import scalafx.scene.image.{Image, ImageView}


object XOPiece {
  def getImage(name: String): ImageView = {
    val image1 = new Image(s"file:images/$name.png")
    val imageView1 = new ImageView(image1)
    imageView1.setFitWidth(120)
    imageView1.setFitHeight(120)
    imageView1
  }
}
