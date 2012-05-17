package Chess.Game

abstract class Player(val board: Board, val color: Color) {
  val allPieces = board.allPieces.filterNot(_._2.isEmpty)
                                 .filter(_._2.get.color == color)
                                 .toSeq

  val movablePieces = allPieces.filterNot(x => x._2.get.getMoves(x._1, board).isEmpty).toSeq

  def nextMove: Move
}

trait PlayerFactory {
  def get(board: Board, color: Color): Player
}