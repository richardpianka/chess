package com.richardpianka.chess.server.storage

import com.richardpianka.chess.common.KeyedWarehouse

class AccountException(message: String) extends Exception(message)

object Accounts {
  import Storables._

  private[this] val warehouse = new KeyedWarehouse[String, Account]("data/accounts.dat",
                                                                    _.getUsername.toLowerCase,
                                                                    Account.parseDelimitedFrom(_))

  warehouse.load()

  private[this] def assertUsernameExists(username: String, value: Boolean = true) {
    if (exists(username) == !value) {
      throw new AccountException(if (value) "That account already exists"
                                 else "That account does not exist")
    }
  }

  def apply(username: String) =
    synchronized {
      assertUsernameExists(username)

      warehouse(username.toLowerCase)
    }

  def exists(username: String) =
    synchronized {
      warehouse.contains(username)
    }

  def matches(username: String, password: String) =
    synchronized {
      this(username).getPassword.equalsIgnoreCase(password)
    }

  def create(username: String, password: String) =
    synchronized {
      assertUsernameExists(username, false)

      //TODO: generate id in constant time
      val id = if (warehouse.all.isEmpty)
                 1
               else
                 warehouse.all.map(_.getId).max + 1
      val account = Account.newBuilder
                           .setId(id)
                           .setUsername(username)
                           .setPassword(password)
                           .setRecord(Record.newBuilder.setWins(0)
                                                       .setLosses(0)
                                                       .setStalemates(0)
                                                       .setDisconnects(0).build)
                           .build

      warehouse.add(account)
      account
    }

  def changePassword(username: String, password: String) =
    synchronized {
      assertUsernameExists(username)

      warehouse(username) = warehouse(username).toBuilder.setPassword(password).build

      warehouse(username)
    }
}