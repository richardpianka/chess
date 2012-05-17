package Chess.Game

import org.junit.Test
import Chess.Players.RandomPlayer

class PlayerTests {
  @Test
  def Random() {
    val playerA = new RandomPlayer
    val playerB = new RandomPlayer

    val game = new Game(playerA, playerB)
    game.play()
  }
}
