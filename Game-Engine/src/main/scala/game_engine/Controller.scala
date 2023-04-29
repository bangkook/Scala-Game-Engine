package game_engine

trait Controller[U] {
  def control(state: U, move: List[String], turn: Boolean): U
}
