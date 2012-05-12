package Chess.Game

abstract class Player(val board: Board, val color: Color) {
  def pieces = board.allPieces.filter(_.color == color)

  def getNextMove: Move
}

trait PlayerFactory {
  def get(board: Board, color: Color): Player
}