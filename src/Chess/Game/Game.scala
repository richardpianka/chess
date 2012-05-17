package Chess.Game

import Colors._
import collection.immutable.HashMap

class Game(playerFactoryA: PlayerFactory, playerFactoryB: PlayerFactory) {
  private[this] var turn = White

  private[this] def changeTurn() {
    turn = turn.opposite
  }

  val board = new Board

  val players = HashMap(White -> playerFactoryA.get(board, White),
                        Black -> playerFactoryB.get(board, Black))

  def play() {
    while (true) {
      board.makeMove(players(turn).nextMove)
      changeTurn()
      println("----------------")
      print(board.toString)
    }
  }
}