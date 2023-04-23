import scalafx.geometry.Pos
import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color._
import scalafx.scene.text.Font


class XOPiece(player: Boolean) {
  def getImage: ImageView = {
    val name = if (player) "x" else "o"
    val image1 = new Image(s"file:src/$name.png")
    val imageView1 = new ImageView(image1)
    imageView1.setFitWidth(120)
    imageView1.setFitHeight(120)
    imageView1
  }

  def getLabel: Label = {
    val label = new Label(if (player) "X" else "O")
    label.setFont(new Font(12))
    label.setTextFill(if (player) Color.Red else Color.Yellow)
    label.setAlignment(Pos.Center)
    label
  }
}
