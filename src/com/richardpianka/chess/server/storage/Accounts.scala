package com.richardpianka.chess.server.storage

import com.richardpianka.chess.common.KeyedWarehouse

class AccountException(message: String) extends Exception(message)

object Accounts {
  import Storables._

  private[this] val warehouse = new KeyedWarehouse[String, Account]("accounts.dat",
                                                                    _.getUsername.toLowerCase,
                                                                    Account.parseDelimitedFrom(_))

  warehouse.load()

  def apply(username: String) = warehouse(username.toLowerCase)

  def create(username: String, password: String) = {
    synchronized {
      //TODO: generate id in constant time
      val id = warehouse.all.map(_.getId).max + 1
      val account = Account.newBuilder
                           .setId(id)
                           .setUsername(username)
                           .setPassword(password)
                           .setRecord(Record.newBuilder.build)
                           .build
      warehouse.add(account)
      account
    }
  }
}