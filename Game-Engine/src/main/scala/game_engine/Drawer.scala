package game_engine

import scalafx.scene.layout.GridPane

trait Drawer[U] {
  def draw(board: U): GridPane
}
