package Chess.Game

import Colors._
import Figurines._
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

  private[this] def winner: Option[Player] = {
    board.capturedPieces.filter(_.figurine == King).map(x => players(x.color)).headOption
  }

  val board = new Board

  val players = HashMap(White -> playerFactoryWhite.get(board, White),
                        Black -> playerFactoryBlack.get(board, Black))

  /**
   * Runs the game loop
   *
   * @return The player that won
   */
  def play(): Option[Player] = {
    var continue = true

    while (continue) {
      val winningPlayer = winner
      if (winningPlayer.isDefined)
        return winningPlayer

      board.makeMove(players(turn).nextMove)
      changeTurn()
      print(board.toString)
      println("----------------")
    }

    None
  }
}