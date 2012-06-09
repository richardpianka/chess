package com.richardpianka
package Chess.Game

import org.junit.Test
import Chess.Players._
import Colors._

class PlayerTests {
  @Test
  def Random() {
    val playerA = new RandomPlayer
    val playerB = new RandomPlayer

    val game = new Game(playerA, playerB)
    game.play()
  }

  @Test
  def Minimax() {
    val playerA = new MinimaxPlayer
    val playerB = new RandomPlayer

    val game = new Game(playerA, playerB)
    val winner = game.play()

    assert(winner.isDefined && winner.get.color == White)
  }

  @Test
  def AlphaBeta() {
    val playerA = new AlphaBetaPlayer
    val playerB = new MinimaxPlayer

    val game = new Game(playerA, playerB)
    val winner = game.play()

    assert(winner.isDefined && winner.get.color == White)
  }
}
