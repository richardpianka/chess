package Chess.Game

import Colors._

class Game(playerFactoryA: PlayerFactory, playerFactoryB: PlayerFactory) {
  val board = new Board

  private[this] val (playerWhite, playerBlack) = (playerFactoryA.get(board, White), playerFactoryB.get(board, Black))

  val players: Map[Color, Player] = Map(White -> playerWhite, Black -> playerBlack)
}
