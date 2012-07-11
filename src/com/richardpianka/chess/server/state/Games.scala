package com.richardpianka.chess.server.state

import collection.mutable

class Game(val name: String)

object Games {
  private[this] val allItems = new mutable.HashSet[Game]
                               with mutable.SynchronizedSet[Game]
  private[this] def map = all.map(x => x.name.toLowerCase -> x).toMap

  def all = allItems.toIterable

  def apply(name: String) = map(name.toLowerCase)

  def exists(name: String) = map.contains(name.toLowerCase)

  def create(name: String, password: Option[String]) =
    synchronized {
      val game = new Game(name)
      allItems += game
      game
    }
}
