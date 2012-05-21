package Chess.Players

import Chess.Game._
import util.Random

class MinimaxPlayer extends PlayerFactory {
  def get(board: Board, color: Color) = new Player(board, color) {
    def nextMove: Move = {
      val piecesThatCanMove = board.playablePieces.filterNot(_._2.isEmpty) // squares where there is a piece
        .filter(_._2.get.color == color) // where that piece is our color
        .filterNot(x => x._2.get.getMoves(x._1, board).isEmpty) // which can move
        .toSeq
      val random = new Random()
      val piece = piecesThatCanMove(random.nextInt(piecesThatCanMove.size - 1))
      val allMoves = piece._2.get.getMoves(piece._1, board)

      allMoves(random.nextInt(allMoves.size))
    }
  }
}
