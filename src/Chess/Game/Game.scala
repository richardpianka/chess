package Chess.Game

import Colors._

class Game(playerFactoryA: PlayerFactory, playerFactoryB: PlayerFactory) {
  val board = new Board

  val playerWhite = playerFactoryA.get(board, White)

  val playerBlack = playerFactoryB.get(board, Black)
}
