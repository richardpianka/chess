package com.richardpianka.chess.game

/**
 * A chess player
 *
 * @param board The board for this player's game
 * @param color The color of this player
 */
abstract class Player(val board: Board, val color: Color) {
  /**
   * Every piece still on the board
   */
  val playablePieces = board.playablePieces.filter(_._2.color == color)
                                           .toSeq

  /**
   * Every piece that has been captured
   */
  val capturedPieces = board.capturedPieces.filter(_.color == color).toSeq

  /**
   * Every piece that has been captured
   */
  val movablePieces = playablePieces.filterNot(x => x._2.getMoves(x._1, board).isEmpty).toSeq

  /**
   * The API for providing moves to a chess game
   *
   * @return
   */
  def nextMove: Move
}

/**
 * Abstract factory for producing an implementation of Player
 */
trait PlayerFactory {
  /**
   * Create a new player
   *
   * @param board The board for this player's game
   * @param color The color of this player
   * @return A new player implementation
   */
  def get(board: Board, color: Color): Player
}