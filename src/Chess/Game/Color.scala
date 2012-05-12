package Chess.Game

final case class Color(name: String) {
  def opposite = this match {
    case Color("White") => Color("Black")
    case Color("Black") => Color("White")
  }
}

object Colors {
  def White = Color("White")
  def Black = Color("Black")
}