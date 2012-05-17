package Chess.Game

/**
 * Represents a direction in which a piece could move
 *
 * @param name The name of the direction
 * @param rank The displacement of files in this direction
 * @param file The displacement of ranks in this direction
 */
final case class Direction(name: String, rank: Int, file: Int)

object Directions {
  val North = Direction("North", 1, 0)
  val South = Direction("South", -1, 0)
  val East = Direction("East", 0, 1)
  val West = Direction("West", 0, -1)

  val NorthEast = Direction("North East", 1, 1)
  val NorthWest = Direction("North West", 1, -1)
  val SouthEast = Direction("South East", -1, 1)
  val SouthWest = Direction("South West", -1, -1)

  val KnightNorthEast = Direction("Knight North East", 2, 1)
  val KnightSouthEast = Direction("Knight South East", -2, 1)
  val KnightNorthWest = Direction("Knight North West", 2, -1)
  val KnightSouthWest = Direction("Knight South West", -2, -1)
  val KnightWestNorth = Direction("Knight West North", 1, 2)
  val KnightEastNorth = Direction("Knight East North", -1, 2)
  val KnightWestSouth = Direction("Knight West South", 1, -2)
  val KnightEastSouth = Direction("Knight East South", -1, -2)
}
