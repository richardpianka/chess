package com.richardpianka.chess.game

import com.richardpianka.chess.common.LinearBoolean

final case class Piece(color: Color, figurine: Figurine, number: Int) {
  def getMoves(coordinate: Coordinate, board: Board) = figurine.getMoves(coordinate, this, board)

  val hasMoved: LinearBoolean = LinearBoolean()

  val isCaptured: LinearBoolean = LinearBoolean()
}