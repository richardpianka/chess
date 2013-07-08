package com.richardpianka.chess.players

import collection.mutable._
import collection.Iterable
import com.richardpianka.chess.game._
import com.richardpianka.chess.game.Figurines._
import com.richardpianka.chess.common._

class MinimaxPlayer extends PlayerFactory {
  private[this] val scoring = Map(Pawn -> 1,
                                  Rook -> 3,
                                  Knight -> 3,
                                  Bishop -> 3,
                                  Queen -> 9,
                                  King -> 100)

  def get(board: Board, color: Color) = new Player(board, color) {
    private[this] val depth = 3;

    def nextMove: Move = movementTree(board, color, depth).map(_.value)
                                                          .max(Ordering[Int].on((x: MoveNode) => x.score))
                                                          .move

    private[this] def movementTree(board: Board, color: Color, levels: Int): Iterable[Node[MoveNode]] = {
      if (levels <= 0) return Seq()

      val piecesThatCanMove = board.movablePieces(color)
      piecesThatCanMove.map(getMoves(_)).flatten

      for (move <- piecesThatCanMove.map(getMoves(_)).flatten) yield {
        val newBoard = board.makeMoveNewBoard(move)
        val children = movementTree(newBoard, color.opposite, levels - 1)
        val scores = children.map(_.value.score)
        val score = if (children.isEmpty) evaluate(newBoard, color)
                    else minOrMax(scores, this.color == color)

        new Node(new MoveNode(move, newBoard, score), children)
      }
    }

    private[this] def evaluate(board: Board, color: Color) = board.playablePieces
                                                    .map(x => scoring(x._2.figurine) * scoreSign(x._2.color))
                                                    .sum

    private[this] def scoreSign(color: Color) = if (color == this.color) 1 else -1

    private[this] def minOrMax(scores: Iterable[Int], maximize: Boolean) = if (maximize) scores.max
                                                                           else scores.min

    private[this] def getMoves(piecePair: Tuple2[Coordinate, Piece]): Iterable[Move] =
      piecePair._2.getMoves(piecePair._1, board)

    private[this] class MoveNode(val move: Move, val board: Board, val score: Int)
  }
}
