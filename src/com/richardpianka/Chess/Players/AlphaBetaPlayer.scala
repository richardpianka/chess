package com.richardpianka
package Chess.Players

import Chess.Game._
import Figurines._

class AlphaBetaPlayer extends PlayerFactory {
  private[this] val scoring = Map(Pawn -> 1,
                                  Rook -> 3,
                                  Knight -> 3,
                                  Bishop -> 3,
                                  Queen -> 9,
                                  King -> 100)

  def get(board: Board, color: Color) = new Player(board, color) {
    private[this] val depth = 5;

    def nextMove: Move = {
      val piecesThatCanMove = board.movablePieces(color)
      piecesThatCanMove.map(getMoves(_)).flatten
      val moves =
        for (val move <- piecesThatCanMove.map(getMoves(_)).flatten)
          yield alphaBeta(move, board.makeMoveNewBoard(move), color, -1000, 1000, depth)

      moves.max(Ordering[Int].on((x: MoveNode) => x.score)).move
    }

    def alphaBeta(move: Move, board: Board, color: Color, alpha: Int, beta: Int, levels: Int): MoveNode = {
      if (levels <= 0) return new MoveNode(move, board, evaluate(board, color))

      val piecesThatCanMove = board.movablePieces(color)
      piecesThatCanMove.map(getMoves(_)).flatten

      var newAlpha = alpha
      var newBeta = beta
      var continue = true

      if (color == this.color) {
        // maximize
        for (val move <- piecesThatCanMove.map(getMoves(_)).flatten
             if continue) {
          val node = alphaBeta(move, board.makeMoveNewBoard(move), color.opposite, newAlpha, newBeta, levels - 1)
          newAlpha = math.max(alpha, node.score)

          if (newBeta <= newAlpha) continue = false
        }

        new MoveNode(move, board, newAlpha)
      } else {
        // minimize
        for (val move <- piecesThatCanMove.map(getMoves(_)).flatten
             if continue) {
          val node = alphaBeta(move, board.makeMoveNewBoard(move), color.opposite, newAlpha, newBeta, levels - 1)
          newBeta = math.min(beta, node.score)

          if (newBeta <= newAlpha) continue = false
        }

        new MoveNode(move, board, newBeta)
      }
    }

    private[this] def evaluate(board: Board, color: Color) = board.playablePieces
                                                    .map(x => scoring(x._2.figurine) * scoreSign(x._2.color))
                                                    .sum

    private[this] def scoreSign(color: Color) = if (color == this.color) 1 else -1

    private[this] def getMoves(piecePair: Tuple2[Coordinate, Piece]) =
      piecePair._2.getMoves(piecePair._1, board)

    private[this] class MoveNode(val move: Move, val board: Board, val score: Int)
  }
}
