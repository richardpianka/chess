package com.richardpianka.chess.server.state

import collection.mutable
import com.richardpianka.chess.network.Contracts.RoomFlags

class Room(val name: String, val flags: RoomFlags)

object Rooms {
  private[this] val allItems = new mutable.HashSet[Room]
                               with mutable.SynchronizedSet[Room]
  private[this] def map = all.map(x => x.name.toLowerCase -> x).toMap

  create("Lobby", RoomFlags.Public)
  create("Beginner", RoomFlags.Public)
  create("Intermediate", RoomFlags.Public)
  create("Expert", RoomFlags.Public)

  def all = allItems.toIterable

  def apply(name: String) = map(name.toLowerCase)

  def exists(name: String) = map.contains(name.toLowerCase)

  def create(name: String, flags: RoomFlags = RoomFlags.Private) =
    synchronized {
      val room = new Room(name, flags)
      allItems += room
      room
    }
}
