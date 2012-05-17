package Chess.Game

import Colors._
import collection.immutable.HashMap

/**
 * A chess game.  Handles player movement and executes the game loop until the game has ended.
 *
 * @param playerFactoryWhite A factory to produce the white player
 * @param playerFactoryBlack A factory to produce the black player
 */
class Game(playerFactoryWhite: PlayerFactory, playerFactoryBlack: PlayerFactory) {
  private[this] var turn = White

  private[this] def changeTurn() {
    turn = turn.opposite
  }

  val board = new Board

  val players = HashMap(White -> playerFactoryWhite.get(board, White),
                        Black -> playerFactoryBlack.get(board, Black))

  def play() {
    while (true) {
      board.makeMove(players(turn).nextMove)
      changeTurn()
      print(board.toString)
      println("----------------")
    }
  }
}