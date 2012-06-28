package com.richardpianka.chess.players

import util.Random
import com.richardpianka.chess.game._

/**
 * Picks a random possible move, and makes it
 */
class RandomPlayer extends PlayerFactory {
  def get(board: Board, color: Color) = new Player(board, color) {
    def nextMove: Move = {
      val random = new Random()
      val piecesThatCanMove = board.movablePieces(color).toSeq
      val piece = piecesThatCanMove(random.nextInt(piecesThatCanMove.size - 1))
      val allMoves = piece._2.getMoves(piece._1, board)

      allMoves(random.nextInt(allMoves.size))
    }
  }
}
