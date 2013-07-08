package com.richardpianka.chess.server.state

import com.richardpianka.chess.network.Contracts.{Room, RoomFlags}
import com.richardpianka.chess.common.WarehouseLite
import com.richardpianka.chess.network.Contracts

/**
 * A room in the chat environment
 *
 * @param name The name of the room
 * @param flags The flags of the room
 */
class Room(val name: String, val flags: RoomFlags) {

  def asProtobuf = Room.newBuilder().setName(name)
                                    .setFlags(flags)
                                    .build
}

/**
 * An exception related to rooms
 *
 * @param message The description for the exception
 */
class RoomException(message: String) extends Exception(message)

/**
 * Manages the list of rooms
 */
object Rooms extends WarehouseLite[String, Room] {
  // Create the default public rooms
  create("Lobby", RoomFlags.Public)
  create("Beginner", RoomFlags.Public)
  create("Intermediate", RoomFlags.Public)
  create("Expert", RoomFlags.Public)

  /**
   * Defines the function for producing a key from an item
   *
   * @param item The item to be keyed
   * @return A key for the item
   */
  protected def key(item: Room) = item.name

  /**
   * A function for ensuring keys fit a standard pattern.  This function
   * should be idempotent.
   *
   * For example, all strings to a specific casing.
   *
   * @param id The key to normalize
   * @return The true key
   */
  override protected def keyNormalize(id: String) = id.toLowerCase

  /**
   * Creates a new room and adds it to the list of rooms
   *
   * @param name The name of the room
   * @param flags The flags of the room
   * @return The new room
   */
  def create(name: String, flags: RoomFlags = RoomFlags.Private) =
    synchronized {
      if (exists(name)) {
        throw new RoomException("That room already exists.")
      }

      add(new Room(name, flags))
    }
}