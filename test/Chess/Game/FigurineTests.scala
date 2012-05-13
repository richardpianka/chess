package Chess.Game

import org.junit.Test

class FigurineTests {
  @Test
  def Pawn {
    val board = new Board
    val start = Coordinate("D5")

    val whitePawn = Piece(Colors.White, Figurines.Pawn)
    whitePawn.hasMoved.actuate()
    val whiteMoves = whitePawn.getMoves(start, board)
    val whiteExpected = Set(Move(whitePawn, start, Coordinate("D6")))

    assert(whiteMoves.toSet == whiteExpected)

    val blackPawn = Piece(Colors.Black, Figurines.Pawn)
    blackPawn.hasMoved.actuate()
    val blackMoves = blackPawn.getMoves(start, board)
    val blackExpected = Set(Move(blackPawn, start, Coordinate("D4")))

    assert(blackMoves.toSet == blackExpected)

    val twoSquares = Set(Coordinate("B6"), Coordinate("B5"))
    val moves = board.at(Coordinate("B7")).get.getMoves(Coordinate("B7"), board).map(_.end)

    println(moves)

    assert(moves.toSet == twoSquares)
  }

  @Test
  def Rook {
    val board = new Board
    val start = Coordinate("D5")

    val rook = Piece(Colors.Black, Figurines.Rook)
    val moves = rook.getMoves(start, board)
    val expected = Seq(Move(rook, start, Coordinate("D6")),
                       Move(rook, start, Coordinate("D4")),
                       Move(rook, start, Coordinate("D3")),
                       Move(rook, start, Coordinate("D2")),
                       Move(rook, start, Coordinate("E5")),
                       Move(rook, start, Coordinate("F5")),
                       Move(rook, start, Coordinate("G5")),
                       Move(rook, start, Coordinate("H5")),
                       Move(rook, start, Coordinate("C5")),
                       Move(rook, start, Coordinate("B5")),
                       Move(rook, start, Coordinate("A5")))

    assert(moves.toSet == expected.toSet)
  }

  @Test
  def Knight {
    val board = new Board
    val start = Coordinate("D5")

    val knight = Piece(Colors.Black, Figurines.Knight)
    val moves = knight.getMoves(start, board)
    val expected = Seq(Move(knight, start, Coordinate("B6")),
                       Move(knight, start, Coordinate("B4")),
                       Move(knight, start, Coordinate("C3")),
                       Move(knight, start, Coordinate("E3")),
                       Move(knight, start, Coordinate("F6")),
                       Move(knight, start, Coordinate("F4")))

    assert(moves.toSet == expected.toSet)
  }
}
