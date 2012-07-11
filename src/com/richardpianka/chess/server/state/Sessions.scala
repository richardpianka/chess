package com.richardpianka.chess.server.state

import com.richardpianka.chess.network.Connection
import com.richardpianka.chess.server.storage.Storables.Account
import collection.mutable

class Session(val id: Long, val connection: Connection) {
  var username: String = null
  var account: Account = null
}

object Sessions {
  private[this] var id = 0
  private[this] val allItems = new mutable.HashSet[Session]
                               with mutable.SynchronizedSet[Session]
  private[this] def map = all.map(x => x.id -> x).toMap
  private[this] def usernameMap = all.filter(_.account != null)
                                              .map(x => x.account.getUsername.toLowerCase -> x).toMap
  private[this] def connectionMap = all.filter(_.connection != null)
                                                .map(x => x.connection -> x).toMap

  def all = allItems.toIterable

  def apply(id: Long) = map(id)

  def apply(username: String) = usernameMap(username.toLowerCase)

  def apply(connection: Connection) = connectionMap(connection)

  def exists(id: Long) = map.contains(id)

  def exists(username: String) = usernameMap.contains(username.toLowerCase)

  def create(connection: Connection) =
    synchronized {
      id += 1
      val session = new Session(id, connection)
      allItems += session
      session
    }

  //TODO: this might not make sense
  def kill(id: Long) {
    allItems -= map(id)
  }
}