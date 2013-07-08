package com.richardpianka.chess.server.state

import com.richardpianka.chess.common.WarehouseLite

/**
 * A chess game
 *
 * @param name The name of the game
 * @param password The password for the game
 */
class Game(val name: String, val password: Option[String])

/**
 * An exception relating to games
 *
 * @param message The description for the exception
 */
class GameException(message: String) extends Exception(message)

/**
* Manages the list of chess games
 */
object Games extends WarehouseLite[String, Game] {
  /**
   * Defines the function for producing a key from an item
   *
   * @param item The item to be keyed
   * @return A key for the item
   */
  protected def key(item: Game) = item.name

  /**
   * A function for ensuring keys fit a standard pattern
   *
   * For example, all strings to a specific casing.
   *
   * @param id The key to normalize
   * @return The true key
   */
  override protected def keyNormalize(id: String) = id.toLowerCase

  /**
   * Creates a new game and adds it to the list of games
   *
   * @param name The name of the game
   * @param password If the game is passworded, and if so, the password
   * @return The new game object
   */
  def create(name: String, password: Option[String]): Game =
    synchronized {
      if (exists(name)) {
        throw new GameException("That game already exists.")
      }

      add(new Game(name, password))
    }
}