package com.richardpianka
package Chess.Game

import org.junit.Test

class BoardTests {
  @Test
  def MakeMove() {
    val board = new Board
    val move = board.at(Coordinate("B8")).get.getMoves(Coordinate("B8"), board).head
    board.makeMove(move)
    println(board)
  }
}