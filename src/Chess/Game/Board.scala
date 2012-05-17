package Chess.Game

import Colors._
import Figurines._
import collection.mutable.{ListBuffer, HashMap}

/**
 * Represents a chess board
 */
class Board {
  private[this] val figurineOrder = Seq(Rook, Knight, Bishop, Queen, King, Bishop, Knight, Rook)

  private[this] val pieces = HashMap[Coordinate, Piece]()

  private[this] val captured = ListBuffer[Piece]()

  private[this] def initialize(color: Color, backRow: Rank, frontRow: Rank) {
    for (val placement <- File.all.zip(figurineOrder))
      pieces.put(Coordinate(placement._1, backRow), Piece(color, placement._2))

    for (file <- File.all)
      pieces.put(Coordinate(file, frontRow), Piece(color, Pawn))
  }

  initialize(White, Rank('1'), Rank('2'))
  initialize(Black, Rank('8'), Rank('7'))

  /**
   * Every piece that is still on the board
   *
   * @return The sequence of all pieces on the board
   */
  def playablePieces = pieces.keys.map(x => x -> pieces.get(x))

  /**
   * Every piece that has been captured during play
   *
   * @return The sequence of all captured pieces
   */
  def capturedPieces = captured.toSeq

  /**
   * Gets a piece or null by a coordinate of this board
   *
   * @param coordinate The coordinate of the square
   * @return The piece at the square referenced by the coordinate
   */
  def at(coordinate: Coordinate): Option[Piece] =
    if (pieces.contains(coordinate)) Some(pieces(coordinate)) else None

  /**
   * Applies a move to the board, and will automatically handle captures
   *
   * @param move The move to be applied
   */
  def makeMove(move: Move) {
    //TODO: add additional checks here (is the piece on the board, is the piece in the starting position, is the new position in it's moves, etc.)
    if (pieces.contains(move.end)) {
      captured += pieces(move.end)
    }
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

    builder.result()
  }
}
