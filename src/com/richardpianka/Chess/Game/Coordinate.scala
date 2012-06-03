package Chess.Game

/**
 * Describes the rank of a square on the chess board
 *
 * @param notation A character between 1..8 inclusive
 */
final case class Rank(notation: Char) {
  import Rank._

  if (notation < minimum || notation > maximum)
    throw new IllegalArgumentException("Invalid rank notation: " + notation)
}

object Rank {
  /**
   * Ranks 1..8
   *
   * @return A squence of ranks 1..8
   */
  def all: Seq[Rank] = minimum to maximum map (Rank(_))

  /**
   * The lowest rank
   *
   * @return The lowest rank
   */
  def minimum: Char = '1'

  /**
   * The highest rank
   *
   * @return The highest rank
   */
  def maximum: Char = '8'
}

/**
 * Describes the file of a square on the chess board
 *
 * @param notation A character between A..H inclusive
 */
final case class File(notation: Char) {
  import File._

  if (notation < minimum || notation > maximum)
    throw new IllegalArgumentException("Invalid file notation: " + notation)
}

object File {
  /**
   * Files A..H
   *
   * @return A sequence of files A..H
   */
  def all: Seq[File] = minimum to maximum map (File(_))

  /**
   * The lowest file
   *
   * @return The lowest file
   */
  def minimum: Char = 'A'

  /**
   * The highest file
   *
   * @return The highest file
   */
  def maximum: Char = 'H'
}

/**
 * Represents the coordinate of a square on the chess board
 *
 * @param rank The rank of the square
 * @param file The file of the square
 */
final case class Coordinate(file: File, rank: Rank) {
  def notation: String = file.notation + "" + rank.notation

  def addFile(plusFile: Int) = Coordinate(File((file.notation + plusFile).toChar), rank)
  def addRank(plusRank: Int) = Coordinate(file, Rank((rank.notation + plusRank).toChar))

  /**
   * Returns all of the squares on the board in the direction from this coordinate
   *
   * @param direction The direction
   * @param board The board
   * @return The sequence of squares
   */
  def inDirection(direction: Direction, piece: Piece, board: Board): Seq[Coordinate] = {
    val builder = Seq.newBuilder[Coordinate]

    //TODO: bad, using exceptions for control flow
    try {
      val coordinate = Coordinate(File((file.notation + direction.file).toChar), Rank((rank.notation + direction.rank).toChar))
      val squarePiece = board.at(coordinate)

      if (!squarePiece.isEmpty)
        if (squarePiece.get.color == piece.color)
          return Seq()
        else
          return Seq(coordinate)

      builder += coordinate
      builder ++= coordinate.inDirection(direction, piece, board)
    } catch {
      case _: Exception => return Seq()
    }

    builder.result()
  }

  /**
   * Returns a square on the board in the direction from this coordinate
   *
   * @param direction The direction
   * @param board The board
   * @return The square
   */
  def inDirectionSingle(direction: Direction, piece: Piece, board: Board): Option[Coordinate] = {
    //TODO: bad, using exceptions for control flow
    try {
      val coordinate = Coordinate(File((file.notation + direction.file).toChar), Rank((rank.notation + direction.rank).toChar))
      val squarePiece = board.at(coordinate)

      squarePiece match {
        case Some(x) => if (x.color != piece.color) return Some(coordinate)
        case None => return Some(coordinate)
      }
    } catch {
      case _: Exception => return None
    }

    None
  }
}

object Coordinate {
  def apply(notation: String): Coordinate = new Coordinate(File(notation(0)), Rank(notation(1)))
}