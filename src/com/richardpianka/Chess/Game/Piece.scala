package com.richardpianka
package Chess.Game

import Chess.Common.LinearBoolean

final case class Piece(color: Color, figurine: Figurine) {
  def getMoves(coordinate: Coordinate, board: Board) = figurine.getMoves(coordinate, this, board)

  val hasMoved: LinearBoolean = LinearBoolean()

  val isCaptured: LinearBoolean = LinearBoolean()
}