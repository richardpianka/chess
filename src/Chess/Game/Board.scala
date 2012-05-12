package Chess.Game

import collection.mutable.HashMap
import Colors._
import Figurines._

/**
 * Represents a chess board
 */
class Board {
  private[this] val pieces: HashMap[Coordinate, Piece] = HashMap[Coordinate, Piece]()

  def allPieces = pieces.values

  pieces.put(Coordinate("A8"), Piece(Black, Rook))
  pieces.put(Coordinate("B8"), Piece(Black, Knight))
  pieces.put(Coordinate("C8"), Piece(Black, Bishop))
  pieces.put(Coordinate("D8"), Piece(Black, Queen))
  pieces.put(Coordinate("E8"), Piece(Black, King))
  pieces.put(Coordinate("F8"), Piece(Black, Bishop))
  pieces.put(Coordinate("G8"), Piece(Black, Knight))
  pieces.put(Coordinate("H8"), Piece(Black, Rook))

  for (file <- 'A' to 'H')
    pieces.put(Coordinate(File(file), Rank('7')), Piece(Colors.Black, Figurines.Pawn))

  pieces.put(Coordinate("A1"), Piece(White, Rook))
  pieces.put(Coordinate("B1"), Piece(White, Knight))
  pieces.put(Coordinate("C1"), Piece(White, Bishop))
  pieces.put(Coordinate("D1"), Piece(White, Queen))
  pieces.put(Coordinate("E1"), Piece(White, King))
  pieces.put(Coordinate("F1"), Piece(White, Bishop))
  pieces.put(Coordinate("G1"), Piece(White, Knight))
  pieces.put(Coordinate("H1"), Piece(White, Rook))

  for (file <- 'A' to 'H')
    pieces.put(Coordinate(File(file), Rank('2')), Piece(Colors.White, Figurines.Pawn))

  /**
   * Gets a piece or null by a coordinate of this board
   *
   * @param coordinate The coordinate of the square
   * @return The piece at the square referenced by the coordinate
   */
  def at(coordinate: Coordinate): Option[Piece] =
    if (pieces.contains(coordinate)) Some(pieces(coordinate)) else None

  def makeMove(move: Move) {
    //TODO: add additional checks here (is the piece on the board, is the piece in the starting position, is the new position in it's moves, etc.)
    pieces.put(move.end, move.piece)
    pieces.remove(move.start)
    move.piece.hasMoved.actuate()
  }

  override def toString = {
    val builder = new StringBuilder

    for (rank <- Rank.all.reverse) {
      for (file <- File.all) {
        val piece = at(Coordinate(file, rank))
        val notation = piece match {
          case Some(x) => x.figurine.notation
          case None => " "
        }

        builder append notation + " "
      }

      builder append "\n"
    }

    builder.result
  }
}
