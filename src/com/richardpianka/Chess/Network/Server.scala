package com.richardpianka.Chess.Network

import actors.Actor
import java.net._
import java.io._
import collection.mutable._

/**
 *
 */
class Server(val port: Int = 1000) {
  val clients = new ListBuffer[Connection]

  /**
   *
   */
  def start() {
    val sockets = new ServerSocket(port)

    while (true) {
      val socket = sockets.accept()
      val connection = new Connection(socket)
      clients += connection
      connection.start()
    }
  }
}

/**
 *
 */
class Connection(socket: Socket) extends Actor {
  private[this] val in = new BufferedReader(new InputStreamReader(socket.getInputStream))
  private[this] val out = new DataOutputStream(socket.getOutputStream)

  def act() {

  }
}