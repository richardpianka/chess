package Chess
package Game

import Directions._
import Common.LinearBoolean._

trait Figurine {
  def name: String

  def notation: Char = name(0)

  def getMoves(coordinate: Coordinate, piece: Piece, board: Board): Seq[Move]

  protected def inDirections(directions: Seq[Direction], coordinate: Coordinate, piece: Piece, board: Board): Seq[Coordinate] = {
    directions.map(coordinate.inDirection(_, piece, board)).flatten
  }

  protected def inDirectionsSingle(directions: Seq[Direction], coordinate: Coordinate, piece: Piece, board: Board): Seq[Coordinate] = {
    directions.map(coordinate.inDirectionSingle(_, piece, board)).flatten
  }
}

object Figurines {
  object Pawn extends Figurine {
    def name = "Pawn"

    def getMoves(coordinate: Coordinate, piece: Piece, board: Board) = {
      val (x, y, z) = if (piece.color == Colors.Black)
                        (South, SouthWest, SouthEast)
                      else
                        (North, NorthWest, NorthEast)

      val twoSquares = if (!piece.hasMoved &&
                           board.at(coordinate.addRank(x.rank)).isEmpty &&
                           board.at(coordinate.addRank(x.rank*2)).isEmpty)
                         Seq(coordinate.addRank(x.rank*2))
                       else
                         Seq()

      val squares = inDirectionsSingle(Seq(x), coordinate, piece, board)
                      .filter(board.at(_).isEmpty) union
                    inDirectionsSingle(Seq(y, z), coordinate, piece, board)
                      .filter(x => !board.at(x).isEmpty && board.at(x).get.color != piece.color) union
                    twoSquares


      squares.map(Move(piece, coordinate, _))
    }
  }

  object Rook extends Figurine {
    def name = "Rook"

    def getMoves(coordinate: Coordinate, piece: Piece, board: Board) =
      inDirections(Seq(North,
                       South,
                       East,
                       West),
                   coordinate, piece, board).map(Move(piece, coordinate, _))
  }

  object Knight extends Figurine {
    def name = "Knight"

    override def notation ='N'

    def getMoves(coordinate: Coordinate, piece: Piece, board: Board) =
      inDirectionsSingle(Seq(KnightEastNorth,
                             KnightEastSouth,
                             KnightNorthEast,
                             KnightNorthWest,
                             KnightSouthEast,
                             KnightSouthWest,
                             KnightWestNorth,
                             KnightWestSouth),
                         coordinate, piece, board).map(Move(piece, coordinate, _))
  }

  object Bishop extends Figurine {
    def name = "Bishop"

    def getMoves(coordinate: Coordinate, piece: Piece, board: Board) =
      inDirections(Seq(NorthEast,
                       NorthWest,
                       SouthEast,
                       SouthWest),
                   coordinate, piece, board).map(Move(piece, coordinate, _))
  }

  object Queen extends Figurine {
    def name = "Queen"

    def getMoves(coordinate: Coordinate, piece: Piece, board: Board) =
      inDirections(Seq(North,
                       South,
                       East,
                       West,
                       NorthEast,
                       NorthWest,
                       SouthEast,
                       SouthWest),
                   coordinate, piece, board).map(Move(piece, coordinate, _))
  }

  object King extends Figurine {
    def name = "King"

    def getMoves(coordinate: Coordinate, piece: Piece, board: Board) =
      inDirectionsSingle(Seq(North,
                             South,
                             East,
                             West,
                             NorthEast,
                             NorthWest,
                             SouthEast,
                             SouthWest),
                         coordinate, piece, board).map(Move(piece, coordinate, _))
  }
}

