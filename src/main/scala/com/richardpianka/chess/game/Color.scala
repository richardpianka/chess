package com.richardpianka.chess.game

/**
 * Defines a the color of a chess piece or player
 *
 * @param name The name of the color
 */
final case class Color(name: String) {
  /**
   * The opposite color
   *
   * @return The opposite color
   */
  def opposite = this match {
    case Color("White") => Color("Black")
    case Color("Black") => Color("White")
  }
}

object Colors {
  def White = Color("White")
  def Black = Color("Black")
}