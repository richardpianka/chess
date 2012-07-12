package com.richardpianka.chess.server.state

import com.richardpianka.chess.network.Connection
import com.richardpianka.chess.server.storage.Storables.Account
import collection.mutable
import com.richardpianka.chess.common.WarehouseLite

/**
 * A user session
 *
 * @param id The unique identifier for this session
 * @param connection The connection for this session
 */
class Session(val id: Long, val connection: Connection) {
  var username: String = null
  var account: Account = null
}

/**
 * Manages the list of sessions
 */
object Sessions extends WarehouseLite[Connection, Session] {
  private[this] var id = 0

  /**
   * Defines the function for producing a key from an item
   *
   * @param item The item to be keyed
   * @return A key for the item
   */
  protected def key(item: Session) = item.connection

  //TODO: this implementation is flimsy
  // used by the following two methods
  private[this] def usernameMap = all.map(x => x.username.toLowerCase -> x).toMap

  /**
   * Gets a session by username
   *
   * @param username The username by which to retrieve a session
   * @return The session
   */
  def apply(username: String) = usernameMap(username)

  /**
   * Whether or not a username is currently in use
   *
   * @param username The username to check
   * @return Whether or not the username is currently in use
   */
  def exists(username: String) = usernameMap.contains(username)

  /**
   * Creates a new session
   *
   * @param connection The connection for this session
   * @return The new session
   */
  def create(connection: Connection) =
    synchronized {
      id += 1
      add(new Session(id, connection))
    }

  /**
   * Removes a session from the list by its connection
   *
   * @param connection The connection whose session to kill
   * @return The session that was killed
   */
  def kill(connection: Connection) = remove(this(connection))
}